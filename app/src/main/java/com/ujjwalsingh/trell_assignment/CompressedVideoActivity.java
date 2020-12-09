package com.ujjwalsingh.trell_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

public class CompressedVideoActivity extends AppCompatActivity {

    Button play;
    VideoView videoView;
    Button pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compressed_video);
        play = findViewById(R.id.play);
        videoView = findViewById(R.id.videoView);
        String input_video = getIntent().getStringExtra("video_uri");
        Uri videoUri = Uri.parse(input_video);

        videoView.setVideoURI(videoUri);
        videoView.start();
        pause = findViewById(R.id.pause);
    }
}