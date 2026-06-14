package com.musicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaylistScreen(playlistId: String? = null) {
    var playlistName by remember { mutableStateOf("My Playlist") }
    var isEditing by remember { mutableStateOf(false) }
    
    val tracks = remember {
        listOf(
            PlaylistTrack("Midnight Dreams", "Alex Rivers", "3:45"),
            PlaylistTrack("Electric Heart", "Neon Dreams", "3:18"),
            PlaylistTrack("Golden Hour", "JVKE", "3:12")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C24), Color(0xFF0A0A0F))
                )
            )
    ) {
        // Header with playlist art
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF9D4EDD), Color(0xFFFF66C4))
                    )
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isEditing) {
                    TextField(
                        value = playlistName,
                        onValueChange = { playlistName = it },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp, color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                } else {
                    Text(playlistName, fontSize = 28.sp, color = Color.White)
                }
                
                Text("${tracks.size} songs • 10 min", color = Color.White.copy(alpha = 0.7f))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { /* Shuffle play */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("SHUFFLE PLAY", color = Color(0xFF9D4EDD))
                    }
                    
                    OutlinedButton(
                        onClick = { isEditing = !isEditing },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text(if (isEditing) "SAVE" else "EDIT")
                    }
                }
            }
        }
        
        // Tracks list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tracks) { track ->
                TrackItem(track)
            }
        }
    }
}

@Composable
fun TrackItem(track: PlaylistTrack) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1C1C24)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF9D4EDD))
                ) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                Column {
                    Text(track.title, color = Color.White, fontSize = 16.sp)
                    Text(track.artist, color = Color.Gray, fontSize = 12.sp)
                }
            }
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(track.duration, color = Color.Gray, fontSize = 12.sp)
                IconButton(onClick = {}) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

data class PlaylistTrack(val title: String, val artist: String, val duration: String)
