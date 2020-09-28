package com.example.musicresources;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;
    MediaPlayer currentPlayer = null;
    String currentSong = null;

    Button playButton;
    TextView mainLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer1 = MediaPlayer.create(this, R.raw.elvis_tutty_frutty);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.elvis_jailhouse_rock);

        playButton = findViewById(R.id.button);
        mainLabel = findViewById(R.id.labelMain);
    }

    public void onPlayClick(View view) {
        if (currentPlayer == null) {
            currentPlayer = mediaPlayer1;
            currentSong = "Tutti Frutty";
            currentPlayer.start();
            mainLabel.setText(currentSong +" is playing");
            playButton.setText(R.string.pauseLabel);
        } else if (currentPlayer.isPlaying()) {
            mainLabel.setText(currentSong + " is on pause");
            currentPlayer.pause();
            playButton.setText(R.string.playLabel);
        } else {
            mainLabel.setText(currentSong + " is playing");
            currentPlayer.start();
            playButton.setText(R.string.pauseLabel);
        }
    }

    public void onNextClick(View view) {
        currentPlayer.pause();
        if (currentPlayer == mediaPlayer1) {
            currentPlayer = mediaPlayer2;
            currentSong = "Rock'n'Roll";
            mainLabel.setText(currentSong);
        }
        else {
            currentPlayer = mediaPlayer1;
            currentSong = "Tutti Frutty";
            mainLabel.setText(currentSong);
        }
        currentPlayer.start();
        mainLabel.setText(currentSong + " is playing");
        playButton.setText(R.string.pauseLabel);
    }
}
