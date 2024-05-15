package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.spotify.R;
import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.UserCallback;
import com.spotify.model.Playlist;
import com.spotify.model.User;
import com.spotify.recycler.PlaylistAdapter;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import com.spotify.service.PlayerService;
import com.spotify.service.SpotifyService;
import com.spotify.service.SpotifyServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPlaylist;
    private PlaylistAdapter adapterPlaylist;
    private PlayerService playerService;
    private FloatingActionButton buttonPlayer;
    User me = new User();
    Playlist playlists = new Playlist();
    private SpotifyService spotifyService;
    String mAccessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Intent intent = getIntent();

        List<Playlist> playlistList = new ArrayList<>();
        playlistList.add(playlists);

        // Aqui é apenas para instanciar e não dar erro
        recyclerViewPlaylist = findViewById(R.id.recyclerViewPlaylist);
        recyclerViewPlaylist.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewPlaylist.setAdapter(adapterPlaylist);

        buttonPlayer = findViewById(R.id.btnPlayer);

        buttonPlayer.setOnClickListener(v -> {
            Intent i = new Intent(this, PlayerActivity.class);
            startActivity(i);
            finish();
        });

        spotifyService = new SpotifyServiceImpl();

        start();
    }

    public void start() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(LoginActivity.CLIENT_ID, AuthorizationResponse.Type.TOKEN, LoginActivity.REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, LoginActivity.REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == LoginActivity.REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    mAccessToken = response.getAccessToken();
                    spotifyService.getMe(mAccessToken, new UserCallback() {
                        @Override
                        public void onUserReceived(User user) {
                            me = user;
                            spotifyService.getPlaylistsByUserId(mAccessToken, user.getId(), new PlaylistCallback() {
                                @Override
                                public void onPlaylistReceived(Playlist playlist) {
                                    playlists = playlist;
                                    Log.e("LoginActivity", playlist.toString());
                                    runOnUiThread(HomeActivity.this::connected);
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    e.printStackTrace();
                                }
                            });

                        }
                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case ERROR:
                    break;
                default:
            }
        }
    }

    private void connected() {
//        Playlist playlist = (Playlist) intent.getSerializableExtra("Playlists");
//        User me = (User) intent.getSerializableExtra("Me");

//        Log.e("Home", "to aqui");
//        Log.e("HomeConnected", String.valueOf(playlists));
//        Log.e("HomeConnected", String.valueOf(me));
//
//        List<Playlist> playlistList = new ArrayList<>();
//        playlistList.add(playlists);

        recyclerViewPlaylist = findViewById(R.id.recyclerViewPlaylist);

        adapterPlaylist = new PlaylistAdapter(this, playlists.getItems());

        recyclerViewPlaylist.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerViewPlaylist.setAdapter(adapterPlaylist);
    }
}