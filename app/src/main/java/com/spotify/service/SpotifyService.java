package com.spotify.service;

import com.spotify.callback.PlaylistCallback;
import com.spotify.callback.UserCallback;

public interface SpotifyService {
    void getMe(String token, UserCallback callback);
    void getPlaylistsByUserId(String token, String userId, PlaylistCallback callback);
}
