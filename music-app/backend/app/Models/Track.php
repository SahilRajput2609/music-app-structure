<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Track extends Model
{
    protected $fillable = [
        'title', 'artist', 'album', 'duration_ms', 'explicit',
        'genre', 'tempo', 'valence', 'danceability',
        'cover_small_url', 'cover_large_url'
    ];
    
    public function getStreamKey($quality)
    {
        $qualities = [
            'medium' => '128',
            'high' => '256',
            'flac' => 'lossless'
        ];
        
        return "tracks/{$this->id}_{$qualities[$quality]}.m3u8";
    }
}
