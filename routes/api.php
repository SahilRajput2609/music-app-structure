<?php

use Illuminate\Support\Facades\Route;

// Public routes
Route::post('/auth/register', [AuthController::class, 'register']);
Route::post('/auth/login', [AuthController::class, 'login']);

// Protected routes
Route::middleware('auth:api')->group(function () {
    Route::get('/user/profile', [UserController::class, 'profile']);
    Route::get('/tracks', [TrackController::class, 'index']);
    Route::get('/stream/{trackId}', [StreamingController::class, 'getStreamUrl']);
    Route::apiResource('playlists', PlaylistController::class);
    Route::get('/recommendations/daily-mix', [RecommendationController::class, 'dailyMix']);
    Route::get('/feed', [SocialController::class, 'feed']);
});
