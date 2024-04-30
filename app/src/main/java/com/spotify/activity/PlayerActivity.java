package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spotify.R;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.callback.StringCallback;
import com.spotify.service.PlayerService;
import com.spotify.service.PlayerServiceImpl;

import java.io.IOException;
import java.net.URL;

public class PlayerActivity extends AppCompatActivity {
    private boolean isPlaying = true;
    private PlayerService playerService;
    private ImageView imageTrack;
    private TextView textTrack;
    private FloatingActionButton buttonPrevious, buttonPlayPause, buttonSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        buttonSkip      = (FloatingActionButton) findViewById(R.id.buttonSkip     );
        buttonPrevious  = (FloatingActionButton) findViewById(R.id.buttonPrevious );
        buttonPlayPause = (FloatingActionButton) findViewById(R.id.buttonPlayPause);

        textTrack = (TextView)  findViewById(R.id.textTrack);

        imageTrack = (ImageView) findViewById(R.id.imageTrack);

        start();
    }

    public void start() {
        SpotifyAppRemote.connect(this, LoginActivity.connectionParams,
            new Connector.ConnectionListener() {
                public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                    playerService = new PlayerServiceImpl(spotifyAppRemote);

                    playerService.play("spotify:playlist:6xICZD48qrN2bVbX9Yms9F?si=01869bf4ed2d4171");

                    urlToImage(imageTrack);
                    trackToTextView(textTrack);
                    switchImageButton(isPlaying);

                    buttonSkip.setOnClickListener(v -> {
                        playerService.skipToNext();
                        switchImageButton(isPlaying = true);
                    });
                    buttonPrevious.setOnClickListener(v -> {
                        playerService.skipToPrevious();
                        switchImageButton(isPlaying = true);
                    });
                    buttonPlayPause.setOnClickListener(v -> {
                        switchImageButton(isPlaying = playerService.playPause(isPlaying));
                    });
                }
                public void onFailure(Throwable throwable) {
                    Log.e("MyActivity", throwable.getMessage(), throwable);
                }
            });
    }

    private void trackToTextView(TextView textTrack) {
        playerService.getNameTrack(new StringCallback() {
            @Override
            public void onStringReceived(String nameTrack) {
                textTrack.setText(nameTrack);
            }
        });
    }

    private void urlToImage(ImageView imageView){
        playerService.getImage(new StringCallback() {
            @Override
            public void onStringReceived(String imageUri) {
                String image = extractImageUrl(imageUri);
                downloadImage(image, imageView);
            }
        });
    }

    private String extractImageUrl(String imageUri){
        String imageId = imageUri.substring(imageUri.lastIndexOf(":") + 1, imageUri.length() - 2);
        return "https://i.scdn.co/image/" + imageId;
    }

    private void downloadImage(String imageUri, ImageView imageTrack){
        new Thread(() -> {
            try {
                URL url = new URL(imageUri);
                final Bitmap bmp;
                bmp = BitmapFactory
                        .decodeStream(url.openConnection()
                                .getInputStream());
                new Handler(Looper.getMainLooper()).post(() -> {
                    imageTrack.setImageBitmap(bmp);
                });
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void switchImageButton(boolean isPlaying) {
        if (isPlaying){
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        }else {
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
        }
    }

}