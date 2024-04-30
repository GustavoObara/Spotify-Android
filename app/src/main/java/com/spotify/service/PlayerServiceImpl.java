package com.spotify.service;

import android.util.Log;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.UserApi;
import com.spotify.callback.StringCallback;
import com.spotify.model.Playlist;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.types.Track;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerApi playerService;
    private String imageUri;
    private String nameTrack;

    public PlayerServiceImpl(SpotifyAppRemote spotifyAppRemote) {
        playerService = spotifyAppRemote.getPlayerApi();
    }

    public void play(String s) {
        playerService.play(s);
    }

    public void pause() {
        playerService.pause();
    }

    public void resume() {
        playerService.resume();
    }

    @Override
    public void skipToNext() {
        playerService.skipNext();
    }

    @Override
    public void skipToPrevious() {
        playerService.skipPrevious();
    }

    public boolean playPause(boolean isPlaying) {
        if(isPlaying) {
            pause();
        } else {
            resume();
        }
        return !isPlaying;
    }

    public void getImage(StringCallback callback) {
        new Thread(() -> {
            playerService.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        this.imageUri = String.valueOf(track.imageUri);
                        callback.onStringReceived(imageUri);
                    }
                });
        }).start();
    }

    public void getNameTrack(StringCallback callback) {
        new Thread(() -> {
            playerService.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        this.nameTrack = track.name;
                        callback.onStringReceived(nameTrack);
                    }
                });
        }).start();
    }

}