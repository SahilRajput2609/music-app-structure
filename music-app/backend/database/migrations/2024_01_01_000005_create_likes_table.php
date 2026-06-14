<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('likes', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->foreignId('track_id')->constrained()->onDelete('cascade');
            $table->unique(['user_id', 'track_id']);
            $table->timestamps();
        });
        
        Schema::create('follows', function (Blueprint $table) {
            $table->id();
            $table->foreignId('follower_id')->constrained('users')->onDelete('cascade');
            $table->foreignId('followee_id')->constrained('users')->onDelete('cascade');
            $table->unique(['follower_id', 'followee_id']);
            $table->timestamps();
        });
        
        Schema::create('stream_logs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->foreignId('track_id')->constrained()->onDelete('cascade');
            $table->string('quality');
            $table->string('ip')->nullable();
            $table->timestamps();
        });
        
        Schema::create('recommendation_feedback', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->foreignId('track_id')->constrained()->onDelete('cascade');
            $table->enum('rating', ['like', 'dislike']);
            $table->timestamps();
        });
    }
    
    public function down()
    {
        Schema::dropIfExists('recommendation_feedback');
        Schema::dropIfExists('stream_logs');
        Schema::dropIfExists('follows');
        Schema::dropIfExists('likes');
    }
};
