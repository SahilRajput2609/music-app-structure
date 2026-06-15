<?php

namespace App\Http\Controllers;

use App\Models\Playlist;
use App\Models\Track;
use Illuminate\Http\Request;

class PlaylistController extends Controller
{
    public function index()
    {
        $playlists = auth()->user()->playlists;
        return response()->json($playlists);
    }
    
    public function store(Request $request)
    {
        $request->validate(['name' => 'required|string|max:255']);
        
        $playlist = auth()->user()->playlists()->create([
            'name' => $request->name,
            'description' => $request->description,
            'is_public' => $request->is_public ?? true
        ]);
        
        return response()->json($playlist, 201);
    }
    
    public function show($id)
    {
        $playlist = Playlist::with('tracks')->findOrFail($id);
        
        if (!$playlist->is_public && $playlist->user_id !== auth()->id()) {
            return response()->json(['error' => 'Unauthorized'], 403);
        }
        
        return response()->json($playlist);
    }
    
    public function update(Request $request, $id)
    {
        $playlist = Playlist::where('id', $id)
            ->where('user_id', auth()->id())
            ->firstOrFail();
            
        $playlist->update($request->only(['name', 'description', 'is_public', 'is_collaborative']));
        
        return response()->json($playlist);
    }
    
    public function destroy($id)
    {
        $playlist = Playlist::where('id', $id)
            ->where('user_id', auth()->id())
            ->firstOrFail();
            
        $playlist->delete();
        
        return response()->json(['message' => 'Deleted']);
    }
    
    public function addTrack(Request $request, $playlistId)
    {
        $playlist = Playlist::where('id', $playlistId)
            ->where('user_id', auth()->id())
            ->firstOrFail();
            
        $position = $playlist->tracks()->max('position') + 1;
        
        $playlist->tracks()->attach($request->track_id, ['position' => $position]);
        
        return response()->json(['message' => 'Track added']);
    }
    
    public function removeTrack($playlistId, $trackId)
    {
        $playlist = Playlist::where('id', $playlistId)
            ->where('user_id', auth()->id())
            ->firstOrFail();
            
        $playlist->tracks()->detach($trackId);
        
        return response()->json(['message' => 'Track removed']);
    }
}
