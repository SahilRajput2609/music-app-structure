<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Playlist extends Model
{
    protected $fillable = [
        'user_id', 'name', 'description', 'is_public', 'is_collaborative', 'cover_art_url'
    ];
    
    protected $casts = [
        'is_public' => 'boolean',
        'is_collaborative' => 'boolean'
    ];
    
    public function user()
    {
        return $this->belongsTo(User::class);
    }
    
    public function tracks()
    {
        return $this->belongsToMany(Track::class, 'playlist_track')
            ->withPivot('position')
            ->orderBy('pivot_position');
    }
}
