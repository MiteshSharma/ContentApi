package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 22/12/16.
 */
@Entity(name = "project_api_keys")
public class ProjectApiKey {
    @JsonIgnore
    @Id
    private ObjectId _id;
    @Property("projectId")
    private String projectId;
    @Property("environmentId")
    private String environmentId;
    @Property("apiKey")
    private String apiKey;

    public ProjectApiKey() {}

    public ProjectApiKey(String id, String projectId, String environmentId, String apiKey) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.projectId = projectId;
        this.environmentId = environmentId;
        this.apiKey = apiKey;
    }

    @JsonIgnore
    public ObjectId getId() {
        return _id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }
}
