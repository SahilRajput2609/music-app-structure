<?php

namespace App\Http\Controllers;

use App\Models\User;
use App\Models\ListeningHistory;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class SocialController extends Controller
{
    public function feed(Request $request)
    {
        $user = auth()->user();
        $followingIds = $user->following()->pluck('users.id');
        
        $feed = ListeningHistory::with('user', 'track')
            ->whereIn('user_id', $followingIds)
            ->orderBy('created_at', 'desc')
            ->limit(50)
            ->get()
            ->map(function($item) {
                return [
                    'user_name' => $item->user->name,
                    'user_avatar' => $item->user->avatar_url,
                    'track_name' => $item->track->title,
                    'artist' => $item->track->artist,
                    'action' => $item->completed ? 'completed' : 'listened_to',
                    'time_ago' => $item->created_at->diffForHumans()
                ];
            });
            
        return response()->json($feed);
    }
    
    public function follow($userId)
    {
        $user = auth()->user();
        $userToFollow = User::findOrFail($userId);
        
        $user->following()->attach($userId);
        
        return response()->json(['message' => "Now following {$userToFollow->name}"]);
    }
    
    public function unfollow($userId)
    {
        $user = auth()->user();
        $user->following()->detach($userId);
        
        return response()->json(['message' => 'Unfollowed']);
    }
    
    public function userPlaylists($userId)
    {
        $user = User::findOrFail($userId);
        $playlists = $user->playlists()->where('is_public', true)->get();
        
        return response()->json($playlists);
    }
    
    public function followers()
    {
        $user = auth()->user();
        return response()->json($user->followers);
    }
    
    public function following()
    {
        $user = auth()->user();
        return response()->json($user->following);
    }
}
