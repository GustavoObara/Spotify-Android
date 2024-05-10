package com.spotify.callback;

import com.spotify.model.Playlist;
import com.spotify.model.User;

public interface PlaylistCallback {
    void onUserReceived(Playlist playlist);
    void onFailure(Exception e);
}
