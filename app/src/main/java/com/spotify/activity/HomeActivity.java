package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.spotify.R;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.recycler.PlaylistAdapter;
import com.spotify.service.PlayerService;
import com.spotify.service.PlayerServiceImpl;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPlaylist;
    private PlaylistAdapter adapterPlaylist;
    private PlayerService playerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        recyclerViewPlaylist = findViewById(R.layout.recyclerViewPlaylist);

        recyclerViewPlaylist.setLayoutManager(new GridLayoutManager(this, 2));

        start();

    }

    public void start() {
        SpotifyAppRemote.connect(this, LoginActivity.connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        playerService = new PlayerServiceImpl(spotifyAppRemote);
                    }
                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);
                    }
                });
    }
}