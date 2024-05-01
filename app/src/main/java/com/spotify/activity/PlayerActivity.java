package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spotify.R;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.callback.LongCallback;
import com.spotify.callback.StringCallback;
import com.spotify.service.PlayerService;
import com.spotify.service.PlayerServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {
    private boolean isPlaying = true;
    private PlayerService playerService;
    private ImageView imageTrack;
    private TextView textTrack, textArtist, textDurationState, textDurationMax;
    private SeekBar barTrack;

    private FloatingActionButton buttonPrevious, buttonPlayPause, buttonSkip, buttonRandom, buttonRepeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        buttonSkip      = (FloatingActionButton) findViewById(R.id.buttonSkip     );
        buttonRandom    = (FloatingActionButton) findViewById(R.id.buttonRandom   );
        buttonRepeat    = (FloatingActionButton) findViewById(R.id.buttonRepeat   );
        buttonPrevious  = (FloatingActionButton) findViewById(R.id.buttonPrevious );
        buttonPlayPause = (FloatingActionButton) findViewById(R.id.buttonPlayPause);

        barTrack = (SeekBar) findViewById(R.id.seekBarMusic);

        textTrack         = (TextView) findViewById(R.id.textTrack        );
        textArtist        = (TextView) findViewById(R.id.textArtist       );
        textDurationMax   = (TextView) findViewById(R.id.textDurationMax  );
        textDurationState = (TextView) findViewById(R.id.textDurationState);

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
                    trackToTextViewTrack(textTrack);
                    trackToTextViewArtist(textArtist);
                    switchImageButton(isPlaying);
                    trackDuration(textDurationMax);
                    updateCurrentPosition(textDurationState);


                    buttonSkip.setOnClickListener(v -> {
                        playerService.skipToNext();
                        switchImageButton(isPlaying = true);
                    });

                    buttonPrevious.setOnClickListener(v -> {
                        playerService.skipToPrevious();
                        switchImageButton(isPlaying = true);
                    });

                    buttonPlayPause.setOnClickListener(v -> switchImageButton(isPlaying = playerService.playPause(isPlaying)));

                    buttonRandom.setOnClickListener(v -> playerService.shuffle());

                    buttonRepeat.setOnClickListener(v -> playerService.repeat());

                    playerService.getDuration(new LongCallback() {
                        @Override
                        public void onLongReceived(Long duration) {
                            barTrack.setMax(duration.intValue());
                        }
                    });

                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            seekBarProgress();
                        }
                    }, 0, 900);

                    barTrack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                }
                public void onFailure(Throwable throwable) {
                    Log.e("MyActivity", throwable.getMessage(), throwable);
                }
            });
    }

    private void trackToTextViewTrack(TextView textTrack) {
        playerService.getNameTrack(new StringCallback() {
            @Override
            public void onStringReceived(String nameTrack) {
                textTrack.setText(nameTrack);
            }
        });
    }

    private void trackToTextViewArtist(TextView textTrack) {
        playerService.getNameArtist(new StringCallback() {
            @Override
            public void onStringReceived(String nameTrack) {
                textArtist.setText(nameTrack);
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

    private void seekBarProgress(){
        playerService.getCurrentPlaybackPosition(new LongCallback() {
            @Override
            public void onLongReceived(Long playbackPosition) {
                barTrack.setProgress(playbackPosition.intValue());
            }
        });
    }

    private void trackDuration(TextView duration){
        playerService.getDuration(new LongCallback() {
            @Override
            public void onLongReceived(Long l) {
                long totalSeconds = l / 1000; // Convert from ms to seconds
                long minutes = totalSeconds / 60;
                long seconds = totalSeconds % 60;
                String formattedTime = String.format("%02d:%02d", minutes, seconds);
                duration.setText(formattedTime);
            }
        });
    }

    private void updateCurrentPosition(TextView position) {
        playerService.getCurrentPlaybackPosition(new LongCallback() {
            @Override
            public void onLongReceived(Long playbackPosition) {
                long currentPositionSeconds = playbackPosition / 1000;
                long minutes = currentPositionSeconds / 60;
                long seconds = currentPositionSeconds % 60;
                String formattedTime = String.format("%02d:%02d", minutes, seconds);
                position.setText(formattedTime);
            }
        });

    }

}