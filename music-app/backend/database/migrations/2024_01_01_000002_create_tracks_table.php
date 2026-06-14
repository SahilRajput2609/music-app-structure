<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('tracks', function (Blueprint $table) {
            $table->id();
            $table->string('title');
            $table->string('artist');
            $table->string('album')->nullable();
            $table->integer('duration_ms');
            $table->boolean('explicit')->default(false);
            $table->string('genre')->nullable();
            $table->float('tempo')->nullable();
            $table->float('valence')->nullable();
            $table->float('danceability')->nullable();
            $table->string('cover_small_url');
            $table->string('cover_large_url');
            $table->timestamps();
        });
    }
    
    public function down()
    {
        Schema::dropIfExists('tracks');
    }
};
