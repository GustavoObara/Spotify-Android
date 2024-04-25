package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spotify.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;

import java.io.IOException;
import java.net.URL;

public class PlayerActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "9b29ba638ee846a2b3d5784dabc922f5";
    private static final String REDIRECT_URI = "http://localhost:8080";

    private boolean isPlaying = true;
    private SpotifyAppRemote mSpotifyAppRemote;
    private ImageView imageTrack;

    private TextView textTrack;

    FloatingActionButton buttonPrevious, buttonPlayPause, buttonSkip;
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
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;

                        // Now you can start interacting with App Remote
                        urlToImage(imageTrack);

                        buttonSkip.setOnClickListener(v -> {
                            skipToNext(v);
                        });

                        buttonPrevious.setOnClickListener(v -> {
                            skipToPrevious(v);
                        });

                        buttonPlayPause.setOnClickListener(v -> {
                            playPauseTrack(v);
                        });
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    public void skipToNext(View view){
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void skipToPrevious(View view){
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    public void playPauseTrack(View view){
        if(isPlaying){
            mSpotifyAppRemote.getPlayerApi().pause();
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play);
        } else {
            mSpotifyAppRemote.getPlayerApi()
                    .subscribeToPlayerState()
                    .setEventCallback(playerState -> {
                        final Track track = playerState.track;
                        mSpotifyAppRemote.getPlayerApi().play(track.uri);
                    });
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        }
        isPlaying = !isPlaying;
    }

    private void urlToImage(ImageView imageTrack){
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        String trackName = String.valueOf(track.name);
                        textTrack.setText(trackName);
                        String imageUri = String.valueOf(track.imageUri);
                        imageUri = extractImageUrl(imageUri);
                        downloadImage(imageUri, imageTrack);
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
}