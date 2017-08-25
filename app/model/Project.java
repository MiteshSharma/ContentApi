package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@Entity(name = "projects")
@Indexes({
        @Index(value = "app_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "appId")}),
        @Index(value = "user_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "userId")})
})
public class Project extends TemporalModel {
    public static final String COLUMN_NAME_PROJECT_NAME = "name";
    @Id
    private ObjectId _id;
    @Property(COLUMN_NAME_PROJECT_NAME)
    private String name;
    @Property("displayName")
    private String displayName;
    @Property("userId")
    private String userId;

    @Property("totalModel")
    private long totalModel;
    @Property("totalContent")
    private long totalContent;
    @Property("totalMedia")
    private long totalMedia;

    public Project() {}

    public Project(String id, String name, String displayName, String userId) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.name = name;
        this.displayName = displayName;
        this.userId = userId;
    }

    public ObjectId getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public long getTotalModel() {
        return totalModel;
    }

    public long getTotalContent() {
        return totalContent;
    }

    public long getTotalMedia() {
        return totalMedia;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalModel(long totalModel) {
        this.totalModel = totalModel;
    }

    public void setTotalContent(long totalContent) {
        this.totalContent = totalContent;
    }

    public void setTotalMedia(long totalMedia) {
        this.totalMedia = totalMedia;
    }
}
