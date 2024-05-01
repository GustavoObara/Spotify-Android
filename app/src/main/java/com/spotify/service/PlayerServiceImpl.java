package com.spotify.service;

import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.callback.LongCallback;
import com.spotify.callback.StringCallback;
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
        new Thread(() -> {
            playerService.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        String imageUri = String.valueOf(track.imageUri);
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
                        String nameTrack = track.name;
                        callback.onStringReceived(nameTrack);
                    }
                });
        }).start();
    }

    public void getNameArtist(StringCallback callback) {
        new Thread(() -> {
            playerService.subscribeToPlayerState()
                    .setEventCallback(playerState -> {
                        final Track track = playerState.track;
                        if (track != null) {
                            String nameArtist = track.artist.name;
                            callback.onStringReceived(nameArtist);
                        }
                    });
        }).start();
    }

    public void getDuration(LongCallback callback){
        new Thread(() -> {
            playerService.subscribeToPlayerState()
                    .setEventCallback(playerState -> {
                        final Track track = playerState.track;
                        if (track != null) {
                            Long seekBar = track.duration;
                            callback.onLongReceived(seekBar);
                        }
                    });
        }).start();
    }

    public void getCurrentPlaybackPosition(LongCallback callback) {
        playerService.subscribeToPlayerState()
            .setEventCallback(playerState -> {
                if (playerState != null && playerState.track != null) {
                    final long playbackPosition = playerState.playbackPosition;
                    callback.onLongReceived(playbackPosition);
                }
            });
    }

    public void seekTo(Long l) {
        playerService.seekTo(l);
    }

}