package com.example.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    int seekOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String videoPath = "android.resource://" + getPackageName() +"/"+R.raw.panas;
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(videoPath);

        if (savedInstanceState != null) {
            seekOn = savedInstanceState.getInt("seekOn");
            videoView.seekTo(seekOn);
        }
        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(seekOn);
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        seekOn = videoView.getCurrentPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seekOn",videoView.getCurrentPosition());
    }
}
