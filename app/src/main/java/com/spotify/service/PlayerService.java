package com.spotify.service;

import com.spotify.callback.LongCallback;
import com.spotify.callback.StringCallback;

public interface PlayerService {
    void pause();
    void resume();
    void shuffle();
    void repeat();
    void skipToNext();
    void seekTo(Long l);
    void play(String s);
    void skipToPrevious();
    boolean playPause(boolean b);
    void getImage(StringCallback c);
    void getDuration(LongCallback l);
    void getNameTrack(StringCallback c);
    void getNameArtist(StringCallback c);
    void getCurrentPlaybackPosition(LongCallback l);
}
