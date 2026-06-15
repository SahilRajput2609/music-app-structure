<?php

namespace App\Http\Controllers;

use App\Models\Track;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class StreamingController extends Controller
{
    public function getStreamUrl(Request $request, $trackId)
    {
        $user = auth()->user();
        $track = Track::findOrFail($trackId);

        // Check subscription tier for quality
        $quality = $this->getQualityForUser($user);
        
        // Generate streaming URL (you can replace with your CDN)
        $streamUrl = $this->generateStreamUrl($track, $quality);
        
        // Log streaming event
        $this->logStreamingEvent($user->id, $trackId, $quality);

        return response()->json([
            'stream_url' => $streamUrl,
            'expires_at' => now()->addMinutes(30),
            'quality' => $quality,
            'track' => $track
        ]);
    }

    private function getQualityForUser($user)
    {
        return match($user->subscription_tier) {
            'hifi' => 'lossless',
            'premium' => 'high',
            default => 'medium'
        };
    }

    private function generateStreamUrl($track, $quality)
    {
        $qualities = [
            'medium' => '128kbps',
            'high' => '320kbps',
            'lossless' => 'flac'
        ];
        
        // Replace with your actual CDN or storage URL
        return "https://cdn.musicapp.com/tracks/{$track->id}_{$qualities[$quality]}.mp3";
    }

    private function logStreamingEvent($userId, $trackId, $quality)
    {
        DB::table('stream_logs')->insert([
            'user_id' => $userId,
            'track_id' => $trackId,
            'quality' => $quality,
            'timestamp' => now(),
            'ip' => request()->ip()
        ]);
    }
}
