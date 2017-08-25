package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.annotations.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by mitesh on 12/11/16.
 */
@Entity(name = "collections")
@Indexes({
        @Index(value = "collection_name_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "name")}),
        @Index(value = "user_id_idx", fields = {@Field(value = "userId")})
})
public class Collection extends TemporalModel {
    public static final String COLUMN_NAME_COLLECTION_NAME = "name";
    @Id
    private ObjectId _id;
    @Property(COLUMN_NAME_COLLECTION_NAME)
    private String name;
    @Property("displayName")
    private String displayName;
    @Property("description")
    private String description;
    @Property("displayField")
    private String displayField;
    @Property("totalFields")
    private long totalFields;
    @Property("isAuthNeeded")
    private boolean isAuthNeeded;

    @Property("projectId")
    private String projectId;

    @Property("firstPublishedAt")
    private Date firstPublishedAt;
    @Property("publishedCount")
    private Long publishedCount;
    @Property("lastPublishedAt")
    private Date lastPublishedAt;

    @Property("totalContent")
    private long totalContent;

    public Collection() {}

    public Collection(String id, String name, String displayName, String description, String projectId, boolean isAuthNeeded) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.projectId = projectId;
        this.isAuthNeeded = isAuthNeeded;
    }

    public ObjectId getId() {
        return this._id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayField() {
        return displayField;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getProjectId() {
        return projectId;
    }

    public boolean isAuthNeeded() {
        return isAuthNeeded;
    }

    public Date getFirstPublishedAt() {
        return firstPublishedAt;
    }

    public Long getPublishedCount() {
        return publishedCount;
    }

    public Date getLastPublishedAt() {
        return lastPublishedAt;
    }

    public long getTotalFields() {
        return totalFields;
    }

    public void setTotalFields(long totalFields) {
        this.totalFields = totalFields;
    }

    public long getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(long totalContent) {
        this.totalContent = totalContent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }
}
