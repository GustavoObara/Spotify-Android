package com.spotify.callback;

import com.spotify.model.TopTracksUser;

public interface TopTracksUserCallback {
    void onTopTracksUserReceived(TopTracksUser topTracksUser);
    void onFailure(Exception e);

}
