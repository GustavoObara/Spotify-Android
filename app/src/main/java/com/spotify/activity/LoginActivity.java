package com.spotify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import com.spotify.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.UserCallback;
import com.spotify.model.Playlist;
import com.spotify.model.User;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.spotify.service.SpotifyService;
import com.spotify.service.SpotifyServiceImpl;

public class LoginActivity extends AppCompatActivity {
    static final String CLIENT_ID = "9b29ba638ee846a2b3d5784dabc922f5";
    static final String REDIRECT_URI = "http://localhost:8080";
    static final int REQUEST_CODE = 1337;
    User me = new User();
    Playlist playlists = new Playlist();
    private SpotifyService spotifyService;
    String mAccessToken;
    static ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spotifyService = new SpotifyServiceImpl();
    }

    protected void onStart() {
        super.onStart();
    }

    public void start(View view) {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "user-top-read", "app-remote-control"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void connected() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    mAccessToken = response.getAccessToken();
                    Log.e("LoginToken", mAccessToken);
                    spotifyService.getMe(mAccessToken, new UserCallback() {
                        @Override
                        public void onUserReceived(User user) {
                            me = user;
                            spotifyService.getPlaylistsByUserId(mAccessToken, user.getId(), new PlaylistCallback() {
                                @Override
                                public void onPlaylistReceived(Playlist playlist) {
                                    playlists = playlist;
                                    Log.e("LoginActivity", playlist.toString());
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
                    connected();
                    break;
                case ERROR:
                    break;
                default:
            }
        }
    }

}