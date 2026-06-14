<?php

namespace Database\Seeders;

use App\Models\Track;
use Illuminate\Database\Seeder;

class TrackSeeder extends Seeder
{
    public function run()
    {
        $tracks = [
            [
                'title' => 'Midnight Dreams',
                'artist' => 'Alex Rivers',
                'album' => 'Night Visions',
                'duration_ms' => 225000,
                'explicit' => false,
                'genre' => 'Pop',
                'tempo' => 120,
                'valence' => 0.65,
                'danceability' => 0.75,
                'cover_small_url' => 'https://picsum.photos/200/200',
                'cover_large_url' => 'https://picsum.photos/500/500'
            ],
            [
                'title' => 'Electric Heart',
                'artist' => 'Neon Dreams',
                'album' => 'Voltage',
                'duration_ms' => 198000,
                'explicit' => false,
                'genre' => 'Electronic',
                'tempo' => 128,
                'valence' => 0.55,
                'danceability' => 0.85,
                'cover_small_url' => 'https://picsum.photos/200/201',
                'cover_large_url' => 'https://picsum.photos/500/501'
            ],
            [
                'title' => 'Golden Hour',
                'artist' => 'JVKE',
                'album' => 'Golden Hour',
                'duration_ms' => 192000,
                'explicit' => false,
                'genre' => 'Pop',
                'tempo' => 98,
                'valence' => 0.7,
                'danceability' => 0.6,
                'cover_small_url' => 'https://picsum.photos/200/202',
                'cover_large_url' => 'https://picsum.photos/500/502'
            ],
            [
                'title' => 'Blinding Lights',
                'artist' => 'The Weeknd',
                'album' => 'After Hours',
                'duration_ms' => 200000,
                'explicit' => true,
                'genre' => 'Pop',
                'tempo' => 171,
                'valence' => 0.73,
                'danceability' => 0.71,
                'cover_small_url' => 'https://picsum.photos/200/203',
                'cover_large_url' => 'https://picsum.photos/500/503'
            ],
            [
                'title' => 'Lose Control',
                'artist' => 'Teddy Swims',
                'album' => 'Lose Control',
                'duration_ms' => 210000,
                'explicit' => false,
                'genre' => 'Soul',
                'tempo' => 85,
                'valence' => 0.35,
                'danceability' => 0.5,
                'cover_small_url' => 'https://picsum.photos/200/204',
                'cover_large_url' => 'https://picsum.photos/500/504'
            ]
        ];
        
        foreach ($tracks as $track) {
            Track::create($track);
        }
    }
}
