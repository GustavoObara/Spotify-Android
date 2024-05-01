package com.spotify.model;

public class Playlist {
    private int imageResource;
    private String title;

    public Playlist(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }
}
