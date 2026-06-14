<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->string('password');
            $table->string('avatar_url')->nullable();
            $table->date('birth_date')->nullable();
            $table->string('refresh_token')->nullable();
            $table->boolean('gdpr_consent')->default(false);
            $table->boolean('recommendations_enabled')->default(true);
            $table->enum('subscription_tier', ['free', 'premium', 'hifi'])->default('free');
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('users');
    }
};
