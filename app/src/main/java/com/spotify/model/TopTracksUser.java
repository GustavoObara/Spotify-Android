package com.spotify.model;

import java.util.List;

import lombok.Data;

@Data
public class TopTracksUser {
    private List<Item> items;
    private int total;
    private int limit;
    private int offset;
    private String href;
    private String next;
    private String previous;

    public TopTracksUser() {
    }

    public TopTracksUser(List<Item> items, int total, int limit, int offset, String href, String next, String previous) {
        this.items = items;
        this.total = total;
        this.limit = limit;
        this.offset = offset;
        this.href = href;
        this.next = next;
        this.previous = previous;
    }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public int getOffset() { return offset; }
    public void setOffset(int offset) { this.offset = offset; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }

    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }

    @Data
    public static class Item {
        private Album album;
        private List<Artist> artists;
        private List<String> available_markets;
        private int disc_number;
        private int duration_ms;
        private boolean explicit;
        private ExternalIds external_ids;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private boolean is_local;
        private String name;
        private int popularity;
        private String preview_url;
        private int track_number;
        private String type;
        private String uri;

        public Item() {
        }

        public Item(Album album, List<Artist> artists, List<String> available_markets, int disc_number, int duration_ms, boolean explicit, ExternalIds external_ids, ExternalUrls external_urls, String href, String id, boolean is_local, String name, int popularity, String preview_url, int track_number, String type, String uri) {
            this.album = album;
            this.artists = artists;
            this.available_markets = available_markets;
            this.disc_number = disc_number;
            this.duration_ms = duration_ms;
            this.explicit = explicit;
            this.external_ids = external_ids;
            this.external_urls = external_urls;
            this.href = href;
            this.id = id;
            this.is_local = is_local;
            this.name = name;
            this.popularity = popularity;
            this.preview_url = preview_url;
            this.track_number = track_number;
            this.type = type;
            this.uri = uri;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }

        public List<String> getAvailable_markets() {
            return available_markets;
        }

        public void setAvailable_markets(List<String> available_markets) {
            this.available_markets = available_markets;
        }

        public int getDisc_number() {
            return disc_number;
        }

        public void setDisc_number(int disc_number) {
            this.disc_number = disc_number;
        }

        public int getDuration_ms() {
            return duration_ms;
        }

        public void setDuration_ms(int duration_ms) {
            this.duration_ms = duration_ms;
        }

        public boolean isExplicit() {
            return explicit;
        }

        public void setExplicit(boolean explicit) {
            this.explicit = explicit;
        }

        public ExternalIds getExternal_ids() {
            return external_ids;
        }

        public void setExternal_ids(ExternalIds external_ids) {
            this.external_ids = external_ids;
        }

        public ExternalUrls getExternal_urls() {
            return external_urls;
        }

        public void setExternal_urls(ExternalUrls external_urls) {
            this.external_urls = external_urls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIs_local() {
            return is_local;
        }

        public void setIs_local(boolean is_local) {
            this.is_local = is_local;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPopularity() {
            return popularity;
        }

        public void setPopularity(int popularity) {
            this.popularity = popularity;
        }

        public String getPreview_url() {
            return preview_url;
        }

        public void setPreview_url(String preview_url) {
            this.preview_url = preview_url;
        }

        public int getTrack_number() {
            return track_number;
        }

        public void setTrack_number(int track_number) {
            this.track_number = track_number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    @Data
    public static class Album {
        private String album_type;
        private List<Artist> artists;
        private List<String> available_markets;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private List<Image> images;
        private String name;
        private String release_date;
        private String release_date_precision;
        private int total_tracks;
        private String type;
        private String uri;

        public Album() {
        }

        public Album(String album_type, List<Artist> artists, List<String> available_markets, ExternalUrls external_urls, String href, String id, List<Image> images, String name, String release_date, String release_date_precision, int total_tracks, String type, String uri) {
            this.album_type = album_type;
            this.artists = artists;
            this.available_markets = available_markets;
            this.external_urls = external_urls;
            this.href = href;
            this.id = id;
            this.images = images;
            this.name = name;
            this.release_date = release_date;
            this.release_date_precision = release_date_precision;
            this.total_tracks = total_tracks;
            this.type = type;
            this.uri = uri;
        }

        public String getAlbum_type() {
            return album_type;
        }

        public void setAlbum_type(String album_type) {
            this.album_type = album_type;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }

        public List<String> getAvailable_markets() {
            return available_markets;
        }

        public void setAvailable_markets(List<String> available_markets) {
            this.available_markets = available_markets;
        }

        public ExternalUrls getExternal_urls() {
            return external_urls;
        }

        public void setExternal_urls(ExternalUrls external_urls) {
            this.external_urls = external_urls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getRelease_date_precision() {
            return release_date_precision;
        }

        public void setRelease_date_precision(String release_date_precision) {
            this.release_date_precision = release_date_precision;
        }

        public int getTotal_tracks() {
            return total_tracks;
        }

        public void setTotal_tracks(int total_tracks) {
            this.total_tracks = total_tracks;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    @Data
    public static class Artist {
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private String name;
        private String type;
        private String uri;

        public Artist() {
        }

        public Artist(ExternalUrls externalUrls, String href, String id, String name, String type, String uri) {
            this.external_urls = externalUrls;
            this.href = href;
            this.id = id;
            this.name = name;
            this.type = type;
            this.uri = uri;
        }

        public ExternalUrls getExternalUrls() {
            return external_urls;
        }

        public void setExternalUrls(ExternalUrls externalUrls) {
            this.external_urls = externalUrls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    @Data
    public static class ExternalUrls {
        private String spotify;

        public ExternalUrls() {
        }

        public ExternalUrls(String spotify) {
            this.spotify = spotify;
        }

        public String getSpotify() { return spotify; }
        public void setSpotify(String spotify) { this.spotify = spotify; }
    }

    @Data
    public static class ExternalIds {
        private String isrc;

        public ExternalIds() {
        }

        public ExternalIds(String isrc) {
            this.isrc = isrc;
        }

        public String getIsrc() { return isrc; }
        public void setIsrc(String isrc) { this.isrc = isrc; }
    }

    @Data
    public static class Image {
        private int height;
        private String url;
        private int width;

        public Image() {
        }

        public Image(int height, String url, int width) {
            this.height = height;
            this.url = url;
            this.width = width;
        }

        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public int getWidth() { return width; }
        public void setWidth(int width) { this.width = width; }
    }
}
