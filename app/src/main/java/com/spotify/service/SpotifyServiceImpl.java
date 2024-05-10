package com.spotify.service;

import android.util.Log;

import com.google.gson.Gson;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.UserCallback;
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
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

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

    public void getPlaylistsByUserId(String token, String userId, PlaylistCallback callback){

    }

}
