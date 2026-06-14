<?php

namespace App\Http\Controllers;

use App\Models\Track;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Cache;

class RecommendationController extends Controller
{
    public function dailyMix()
    {
        $user = auth()->user();
        
        // Cache for 6 hours
        $cacheKey = "daily_mix_user_{$user->id}";
        
        $recommendations = Cache::remember($cacheKey, 21600, function() use ($user) {
            return $this->generateRecommendations($user);
        });
        
        return response()->json($recommendations);
    }
    
    public function similarTracks($trackId)
    {
        $track = Track::findOrFail($trackId);
        
        // Find tracks with similar genre
        $similar = Track::where('genre', $track->genre)
            ->where('id', '!=', $trackId)
            ->limit(10)
            ->get();
            
        return response()->json($similar);
    }
    
    public function feedback(Request $request)
    {
        $request->validate([
            'track_id' => 'required|exists:tracks,id',
            'rating' => 'required|in:like,dislike'
        ]);
        
        DB::table('recommendation_feedback')->insert([
            'user_id' => auth()->id(),
            'track_id' => $request->track_id,
            'rating' => $request->rating,
            'created_at' => now()
        ]);
        
        // Clear cache to refresh recommendations
        Cache::forget("daily_mix_user_" . auth()->id());
        
        return response()->json(['message' => 'Feedback recorded']);
    }
    
    private function generateRecommendations($user)
    {
        // Get user's liked tracks
        $likedTracks = $user->likes()->pluck('track_id')->toArray();
        
        // Get user's listening history
        $historyTracks = DB::table('listening_history')
            ->where('user_id', $user->id)
            ->orderBy('created_at', 'desc')
            ->limit(50)
            ->pluck('track_id')
            ->toArray();
        
        // Find users with similar tastes
        $similarUserIds = DB::table('listening_history')
            ->select('user_id', DB::raw('COUNT(*) as common'))
            ->whereIn('track_id', array_merge($likedTracks, $historyTracks))
            ->where('user_id', '!=', $user->id)
            ->groupBy('user_id')
            ->orderByDesc('common')
            ->limit(50)
            ->pluck('user_id');
        
        // Get tracks liked by similar users
        $recommendedTrackIds = DB::table('likes')
            ->whereIn('user_id', $similarUserIds)
            ->whereNotIn('track_id', array_merge($likedTracks, $historyTracks))
            ->select('track_id', DB::raw('COUNT(*) as popularity'))
            ->groupBy('track_id')
            ->orderByDesc('popularity')
            ->limit(20)
            ->pluck('track_id');
        
        // Fallback: popular tracks if not enough recommendations
        if ($recommendedTrackIds->count() < 10) {
            $fallbackTracks = Track::inRandomOrder()
                ->limit(20 - $recommendedTrackIds->count())
                ->pluck('id');
            $recommendedTrackIds = $recommendedTrackIds->merge($fallbackTracks);
        }
        
        return Track::whereIn('id', $recommendedTrackIds)->get();
    }
}
