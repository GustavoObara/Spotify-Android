package com.spotify.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.callback.LongCallback;
import com.spotify.callback.StringCallback;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerApi playerService;
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

    public void shuffle() {
        playerService.toggleShuffle();
    }

    public void repeat() {
        playerService.toggleRepeat();
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
        playerService.subscribeToPlayerState()
            .setEventCallback(playerState -> {
                final Track track = playerState.track;
                if (track != null) {
                    String imageUri = String.valueOf(track.imageUri);
                    callback.onStringReceived(imageUri);
                }
            });
    }

    public void getNameTrack(StringCallback callback) {
        playerService.subscribeToPlayerState()
            .setEventCallback(playerState -> {
                final Track track = playerState.track;
                if (track != null) {
                    String nameTrack = track.name;
                    callback.onStringReceived(nameTrack);
                }
            });
    }

    public void getNameArtist(StringCallback callback) {
        playerService.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        String nameArtist = track.artist.name;
                        callback.onStringReceived(nameArtist);
                    }
                });
    }

    public void getDuration(LongCallback callback) {
        playerService.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Long seekBar = track.duration;
                        callback.onLongReceived(seekBar);
                    }
                });
    }

    public void getCurrentPlaybackPosition(LongCallback callback) {
        playerService.subscribeToPlayerState()
            .setEventCallback(playerState -> {
            if (playerState != null && playerState.track != null) {
                final Long playbackPosition = playerState.playbackPosition;
                callback.onLongReceived(playbackPosition);
            }
        });
    }

    public void seekTo(Long l) {
        playerService.seekTo(l);
    }

}