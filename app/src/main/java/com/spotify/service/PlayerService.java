package com.spotify.service;

import com.spotify.callback.LongCallback;
import com.spotify.callback.StringCallback;

public interface PlayerService {
    void play(String s);
    void pause();
    void resume();
    void shuffle();
    void repeat();
    void skipToNext();
    void skipToPrevious();
    boolean playPause(boolean b);
    void getImage(StringCallback c);
    void getNameTrack(StringCallback c);
    void getNameArtist(StringCallback c);
    void getDuration(LongCallback l);
    void getCurrentPlaybackPosition(LongCallback l);
    void seekTo(Long l);
}
