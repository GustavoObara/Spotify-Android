package com.spotify.model;

import java.util.List;

import lombok.Data;

@Data
public class Playlist {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<PlaylistItem> items;

    public Playlist(String href, int limit, String next, int offset, String previous, int total, List<PlaylistItem> items) {
        this.href = href;
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.previous = previous;
        this.total = total;
        this.items = items;
    }

    public Playlist() {
    }



    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PlaylistItem> getItems() {
        return items;
    }
    public void setItems(List<PlaylistItem> items) {
        this.items = items;
    }


    @Data
    public class PlaylistItem {
        private boolean collaborative;
        private String description;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private List<Image> images;
        private String name;
        private Owner owner;
        private String primary_color;
        private boolean isPublic;
        private String snapshot_id;
        private Tracks tracks;

        public PlaylistItem(boolean collaborative, String description, ExternalUrls external_urls, String href, String id, List<Image> images, String name, Owner owner, String primary_color, boolean isPublic, String snapshot_id, Tracks tracks) {
            this.collaborative = collaborative;
            this.description = description;
            this.external_urls = external_urls;
            this.href = href;
            this.id = id;
            this.images = images;
            this.name = name;
            this.owner = owner;
            this.primary_color = primary_color;
            this.isPublic = isPublic;
            this.snapshot_id = snapshot_id;
            this.tracks = tracks;
        }

        public PlaylistItem() {
        }

        public String getImageResource(int position) {
            PlaylistItem item = getPlaylistItemByPosition(position);
            if (item != null && item.getImages() != null && !item.getImages().isEmpty()) {
                return item.getImages().get(0).getUrl();
            }
            return null;
        }

        public String getTitle(int position) {
            PlaylistItem item = getPlaylistItemByPosition(position);
            if (item != null) {
                return item.getName();
            }
            return null;
        }

        public PlaylistItem getPlaylistItemByPosition(int position) {
            if (items != null && !items.isEmpty()) {
                return items.get(position);
            }
            return null;
        }

        public String getImageUrl() {
            if (images != null && !images.isEmpty()) {
                return images.get(0).getUrl();
            } else {
                return null;
            }
        }

        public boolean isCollaborative() {
            return collaborative;
        }

        public void setCollaborative(boolean collaborative) {
            this.collaborative = collaborative;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public String getPrimary_color() {
            return primary_color;
        }

        public void setPrimary_color(String primary_color) {
            this.primary_color = primary_color;
        }

        public boolean isPublic() {
            return isPublic;
        }

        public void setPublic(boolean aPublic) {
            isPublic = aPublic;
        }

        public String getSnapshot_id() {
            return snapshot_id;
        }

        public void setSnapshot_id(String snapshot_id) {
            this.snapshot_id = snapshot_id;
        }

        public Tracks getTracks() {
            return tracks;
        }

        public void setTracks(Tracks tracks) {
            this.tracks = tracks;
        }
    }
}

@Data
class Owner {
    private String display_name;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private String type;
    private String uri;

    public Owner(String display_name, ExternalUrls external_urls, String href, String id, String type, String uri) {
        this.display_name = display_name;
        this.external_urls = external_urls;
        this.href = href;
        this.id = id;
        this.type = type;
        this.uri = uri;
    }

    public Owner() {
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
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
class Tracks {
    private String href;
    private int total;

    public Tracks(String href, int total) {
        this.href = href;
        this.total = total;
    }

    public Tracks() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}

