package com.musicapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

class DownloadManager(private val context: Context) {
    
    companion object {
        private const val CHANNEL_ID = "download_channel"
        private const val CHANNEL_NAME = "Music Downloads"
    }
    
    init {
        createNotificationChannel()
    }
    
    fun downloadTrack(trackId: String, url: String, title: String) {
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
            .addTag("download_$trackId")
            .setInputData(
                workDataOf(
                    "track_id" to trackId,
                    "url" to url,
                    "title" to title
                )
            )
            .build()
        
        WorkManager.getInstance(context).enqueue(workRequest)
        showDownloadNotification(title)
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
    
    private fun showDownloadNotification(title: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Downloading")
            .setContentText(title)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setProgress(100, 0, true)
            .build()
        
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(title.hashCode(), notification)
    }
}

class DownloadWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val trackId = inputData.getString("track_id") ?: return@withContext Result.failure()
            val url = inputData.getString("url") ?: return@withContext Result.failure()
            val title = inputData.getString("title") ?: "Track"
            
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
            
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            
            val outputFile = File(applicationContext.filesDir, "downloads/$trackId.mp3")
            outputFile.parentFile?.mkdirs()
            
            response.body?.byteStream()?.use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
