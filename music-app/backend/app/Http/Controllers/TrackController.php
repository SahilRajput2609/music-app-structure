<?php

namespace App\Http\Controllers;

use App\Models\Track;
use App\Models\ListeningHistory;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class TrackController extends Controller
{
    public function index(Request $request)
    {
        $limit = $request->get('limit', 50);
        $offset = $request->get('offset', 0);
        
        $tracks = Track::skip($offset)->limit($limit)->get();
        
        return response()->json([
            'data' => $tracks,
            'meta' => ['total' => Track::count(), 'limit' => $limit, 'offset' => $offset]
        ]);
    }
    
    public function search(Request $request)
    {
        $query = $request->get('q');
        
        $tracks = Track::where('title', 'LIKE', "%{$query}%")
            ->orWhere('artist', 'LIKE', "%{$query}%")
            ->orWhere('album', 'LIKE', "%{$query}%")
            ->limit(20)
            ->get();
            
        return response()->json($tracks);
    }
    
    public function show($id)
    {
        $track = Track::findOrFail($id);
        return response()->json($track);
    }
    
    public function like($id)
    {
        $user = auth()->user();
        $user->likes()->attach($id);
        return response()->json(['message' => 'Liked']);
    }
    
    public function unlike($id)
    {
        $user = auth()->user();
        $user->likes()->detach($id);
        return response()->json(['message' => 'Unliked']);
    }
    
    public function batchListeningHistory(Request $request)
    {
        $history = $request->get('history', []);
        
        foreach ($history as $item) {
            ListeningHistory::create([
                'user_id' => auth()->id(),
                'track_id' => $item['track_id'],
                'progress_percent' => $item['progress'],
                'completed' => $item['progress'] >= 90
            ]);
        }
        
        return response()->json(['message' => 'History saved']);
    }
}
