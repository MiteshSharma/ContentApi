package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by mitesh on 18/11/16.
 */
@Entity(name = "project_daily_activities")
public class ProjectDailyActivity {
    @Id
    private ObjectId _id;
    @Property("projectId")
    private String projectId;
    @Property("date")
    private Date date;
    @Property("totalModel")
    private long totalModel;
    @Property("totalContent")
    private long totalContent;
    @Property("totalMedia")
    private long totalMedia;

    public ProjectDailyActivity() {}

    public ProjectDailyActivity(ObjectId id, String projectId, Date date, long totalModel, long totalContent, long totalMedia) {
        this._id = id;
        this.projectId = projectId;
        this.date = date;
        this.totalModel = totalModel;
        this.totalContent = totalContent;
        this.totalMedia = totalMedia;
    }

    @JsonIgnore
    public ObjectId getId() {
        return _id;
    }

    @JsonIgnore
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotalModel() {
        return totalModel;
    }

    public void setTotalModel(long totalModel) {
        this.totalModel = totalModel;
    }

    public long getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(long totalContent) {
        this.totalContent = totalContent;
    }

    public long getTotalMedia() {
        return totalMedia;
    }

    public void setTotalMedia(long totalMedia) {
        this.totalMedia = totalMedia;
    }
}
