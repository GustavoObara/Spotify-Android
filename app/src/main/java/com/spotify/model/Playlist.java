package com.spotify.model;

public class Playlist {
    private String id;
    private int imageResource;
    private String title;

    public Playlist(String id, int imageResource, String title) {
        this.id = id;
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