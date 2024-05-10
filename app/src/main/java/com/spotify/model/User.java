package com.spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String display_name;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String type;
    private String uri;
    private Followers followers;
}

@Data
class ExternalUrls {
    private String spotify;
}

@Data
class Followers {
    private String href;
    private int total;
}

@Data
class Image {
    private String url;
    private int height;
    private int width;
}
