package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 20/12/16.
 */
public class MediaObject {
    private String id;
    private String name;
    private String url;

    public MediaObject() {}

    public MediaObject(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @JsonIgnore
    public Media getMedia(String projectId) {
        return new Media(this.id, this.name, this.url, projectId);
    }

    public static MediaObject getMediaObject(Media media) {
        return new MediaObject(media.getId().toString(), media.getName(), media.getUrl());
    }

    public static List<MediaObject> getMediaObjects(List<Media> mediaList) {
        List<MediaObject> mediaObjectList = new ArrayList<>();
        for (Media media: mediaList) {
            mediaObjectList.add(getMediaObject(media));
        }
        return mediaObjectList;
    }
}
