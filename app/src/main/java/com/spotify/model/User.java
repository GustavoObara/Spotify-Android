package com.spotify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String display_name;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String type;
    private String uri;
    private Followers followers;

    public String getId(){
        return id;
    }

}

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
class ExternalUrls {
    private String spotify;
}

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
class Followers {
    private String href;
    private int total;
}

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
class Image {
    private String url;
    private int height;
    private int width;

    public String getUrl() {
        return url;
    }
}
