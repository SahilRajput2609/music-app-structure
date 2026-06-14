<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Storage;

class UserController extends Controller
{
    public function profile()
    {
        $user = auth()->user();
        $stats = [
            'playlists_count' => $user->playlists()->count(),
            'followers_count' => $user->followers()->count(),
            'following_count' => $user->following()->count(),
            'listening_time' => $this->getTotalListeningTime($user->id)
        ];
        
        return response()->json(array_merge($user->toArray(), ['stats' => $stats]));
    }
    
    public function updateProfile(Request $request)
    {
        $user = auth()->user();
        
        $request->validate([
            'name' => 'sometimes|string|max:255',
            'avatar' => 'sometimes|image|max:2048'
        ]);
        
        if ($request->hasFile('avatar')) {
            $path = $request->file('avatar')->store('avatars', 'public');
            $user->avatar_url = Storage::url($path);
        }
        
        $user->update($request->only(['name', 'recommendations_enabled']));
        
        return response()->json($user);
    }
    
    public function deleteAccount()
    {
        $user = auth()->user();
        
        // Delete all user data
        $user->listeningHistory()->delete();
        $user->playlists()->delete();
        $user->likes()->detach();
        $user->following()->detach();
        $user->delete();
        
        return response()->json(['message' => 'Account deleted']);
    }
    
    public function exportData()
    {
        $user = auth()->user();
        
        $data = [
            'profile' => $user->only(['name', 'email', 'birth_date', 'created_at']),
            'listening_history' => $user->listeningHistory()->with('track')->get(),
            'playlists' => $user->playlists()->with('tracks')->get(),
            'likes' => $user->likes()->get()
        ];
        
        return response()->json($data);
    }
    
    private function getTotalListeningTime($userId)
    {
        $totalMs = \DB::table('listening_history')
            ->join('tracks', 'listening_history.track_id', '=', 'tracks.id')
            ->where('user_id', $userId)
            ->where('completed', true)
            ->sum('tracks.duration_ms');
            
        return round($totalMs / 3600000, 1); // Hours
    }
}
