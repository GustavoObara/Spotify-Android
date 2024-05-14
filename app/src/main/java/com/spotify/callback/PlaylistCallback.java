package com.spotify.callback;

import com.spotify.model.Playlist;

public interface PlaylistCallback {

    void onPlaylistReceived(Playlist playlist);
    void onFailure(Exception e);
}
