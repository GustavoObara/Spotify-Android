package com.spotify.model;

import java.util.List;

public class Track {
    private String album_type;
    private int total_tracks;
    private List<String> available_markets;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private String release_date;
    private String releaseDate_precision;
    private Restrictions restrictions;
    private String type;
    private String uri;
    private List<Artist> artists;
}

class Restrictions {
    private String reason;
}