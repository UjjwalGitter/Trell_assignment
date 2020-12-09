package com.ujjwalsingh.trell_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;


public class VideoPreview extends AppCompatActivity {
VideoView videoView;
Button compress;
EditText bitrate_text;
    String input_video;
    String outputPath;
int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        videoView = findViewById(R.id.videoView);
        compress = findViewById(R.id.compress_button);
        bitrate_text = findViewById(R.id.bitrate_text);
        input_video = getIntent().getStringExtra("video_uri");

        Log.i("percy",input_video);

        Uri videoUri = Uri.parse(input_video);

        videoView.setVideoURI(videoUri);
        videoView.start();

        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String bitrate_count = bitrate_text.getText().toString();
                    compress(bitrate_count);
                }catch (Exception e){

                }
            }
        });

    }

    public void compress(String bitrate) throws Exception {

        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFinish() {

                }
            });
        } catch (Exception e) {
            // Handle if FFmpeg is not supported by device
        }
        outputPath = "compressed_vid";
        String[] commandArray = new String[]{};
        commandArray = new String[]{"-y", "-i",input_video , "-s", "720x480", "-r", bitrate,
                "-vcodec", "mpeg4", "-b:v", "300k", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputPath};
        ffmpeg.execute(commandArray, new ExecuteBinaryResponseHandler() {
            @Override
            public void onStart() {
                Log.e("FFmpeg", "onStart");
            }
            @Override
            public void onProgress(String message) {
                Log.e("FFmpeg onProgress? ", message);
            }
            @Override
            public void onFailure(String message) {
                Log.e("FFmpeg onFailure? ", message);
            }
            @Override
            public void onSuccess(String message) {
                Log.e("FFmpeg onSuccess? ", message);

            }
            @Override
            public void onFinish() {
                Intent i = new Intent(VideoPreview.this, CompressedVideoActivity.class);
                i.putExtra("video_uri",outputPath.toString());
                startActivity(i);
                count = count + 1;
            }
        });
    }

}