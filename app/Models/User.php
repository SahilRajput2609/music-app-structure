<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Tymon\JWTAuth\Contracts\JWTSubject;

class User extends Authenticatable implements JWTSubject
{
    use HasFactory, Notifiable;

    protected $fillable = [
        'name', 'email', 'password', 'avatar_url', 'birth_date',
        'gdpr_consent', 'recommendations_enabled', 'subscription_tier'
    ];

    protected $hidden = ['password', 'refresh_token'];

    protected $casts = [
        'birth_date' => 'date',
        'gdpr_consent' => 'boolean',
        'recommendations_enabled' => 'boolean',
    ];

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }

    public function playlists()
    {
        return $this->hasMany(Playlist::class);
    }

    public function likes()
    {
        return $this->belongsToMany(Track::class, 'likes')->withTimestamps();
    }

    public function following()
    {
        return $this->belongsToMany(User::class, 'follows', 'follower_id', 'followee_id');
    }

    public function followers()
    {
        return $this->belongsToMany(User::class, 'follows', 'followee_id', 'follower_id');
    }
}
