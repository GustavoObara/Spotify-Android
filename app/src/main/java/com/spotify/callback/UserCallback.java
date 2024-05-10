package com.spotify.callback;

import com.spotify.model.User;

public interface UserCallback {
    void onUserReceived(User user);
    void onFailure(Exception e);
}
