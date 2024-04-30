package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.spotify.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.service.PlayerService;
import com.spotify.service.PlayerServiceImpl;

public class LoginActivity extends AppCompatActivity {

    static final String CLIENT_ID = "9b29ba638ee846a2b3d5784dabc922f5";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private SpotifyAppRemote appRemote;
    private PlayerService playerService;
    static ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void onStart() {
        super.onStart();
    }

    public void start(View view) {
        SpotifyAppRemote.connect(this, connectionParams,
            new Connector.ConnectionListener() {
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                appRemote = spotifyAppRemote;
                playerService = new PlayerServiceImpl(appRemote);
                connected();
            }
            public void onFailure(Throwable throwable) {
                Log.e("MyActivity", throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(appRemote);
    }

    private void connected() {
        Intent i = new Intent(this, PlayerActivity.class);
        startActivity(i);
        finish();
    }

}