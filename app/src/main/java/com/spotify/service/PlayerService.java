package com.spotify.service;

import com.spotify.callback.StringCallback;

public interface PlayerService {
    void play(String s);
    void pause();
    void resume();
    void skipToNext();
    void skipToPrevious();
    boolean playPause(boolean b);
    void getImage(StringCallback c);
    void getNameTrack(StringCallback c);

}
