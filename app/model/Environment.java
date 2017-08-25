package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 12/11/16.
 */
@Entity(name = "environments")
@Indexes({
        @Index(value = "project_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "projectId")}),
        @Index(value = "user_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "userId")})
})
public class Environment {
    public static final String COLUMN_NAME_ENVIRONMENT_NAME = "name";
    @Id
    private ObjectId _id;
    @Property(COLUMN_NAME_ENVIRONMENT_NAME)
    private String name;
    @Property("displayName")
    private String displayName;
    @Property("description")
    private String description;
    @Property("projectId")
    private String projectId;
    @Property("userId")
    private String userId;

    @Property("totalContent")
    private long totalContent;

    public Environment() {}

    public Environment(String id, String name, String displayName, String description, String projectId, String userId) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.projectId = projectId;
        this.userId = userId;
    }

    public ObjectId getId() {
        return this._id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProjectId() {
        return projectId;
    }

    public  void setId(ObjectId _id) {
        this._id = _id;
    }

    public long getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(long totalContent) {
        this.totalContent = totalContent;
    }
}
