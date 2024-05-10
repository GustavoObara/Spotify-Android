package com.spotify.service;

import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.UserCallback;
import com.spotify.model.User;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public interface SpotifyService {
    void getMe(String token, UserCallback callback);
    void getPlaylistsByUserId(String token, String userId, PlaylistCallback callback);
}
