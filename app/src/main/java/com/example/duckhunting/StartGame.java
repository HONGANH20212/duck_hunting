package com.example.duckhunting;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {
    GameView gameView;
    MediaPlayer bg_music;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        bg_music = MediaPlayer.create(this, R.raw.bg_music);
        if(bg_music != null){
            bg_music.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the music if it's playing
        if(bg_music != null && bg_music.isPlaying()){
            bg_music.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the music if it was playing when paused
        if(bg_music != null && !bg_music.isPlaying()){
            bg_music.start();
        }
    }
}