package com.spotify.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.TopTracksUserCallback;
import com.spotify.callback.UserCallback;
import com.spotify.model.Playlist;
import com.spotify.model.TopTracksUser;
import com.spotify.model.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifyServiceImpl implements SpotifyService {
    OkHttpClient client = new OkHttpClient();

    public SpotifyServiceImpl() {  }

    @Override
    public void getMe(String token, final UserCallback callback) {
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                Gson gson = new Gson();
                Log.e("SpotifyService", responseData);
                User user = gson.fromJson(responseData, User.class);
                Log.e("SpotifyService", user.toString());
                callback.onUserReceived(user);
            }
        });
    }

    public void getPlaylistsByUserId(String token, String userId, PlaylistCallback callback) {
        Log.e("SpotifyService", token);
        Log.e("SpotifyService", userId);

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/users/" + userId + "/playlists")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseData = response.body().string();
                Gson gson = new Gson();
                Playlist playlist = gson.fromJson(responseData, Playlist.class);
                Log.e("SpotifyService", playlist.toString());
                callback.onPlaylistReceived(playlist);
            }
        });
    }

    @Override
    public void getTopTrackUser(String token, TopTracksUserCallback callback) {
        Log.e("getTopTrackUser", token);
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=10&offset=5")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseData = response.body().string();
                Gson gson = new Gson();
                TopTracksUser topTracksUser = gson.fromJson(responseData, TopTracksUser.class);
                Log.e("SpotifyService", topTracksUser.toString());
                callback.onTopTracksUserReceived(topTracksUser);
            }
        });
    }

}

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected code " + response);
//                }
//                // realizar o que fazer com a resposta aqui
//            }