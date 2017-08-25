package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 20/12/16.
 */
@Entity(name = "medias")
public class Media extends TemporalModel {
    @Id
    private ObjectId _id;
    @Property("name")
    private String name;
    @Property("url")
    private String url;
    @Property("projectId")
    private String projectId;

    public Media() {}

    public Media(String id, String name, String url, String projectId) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.name = name;
        this.url = url;
        this.projectId = projectId;
    }

    public Object getId() {
        return this._id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getProjectId() {
        return projectId;
    }
}
