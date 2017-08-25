package model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Created by mitesh on 14/11/16.
 */
@Entity(name = "teams")
@Indexes({
        @Index(value = "project_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "projectId")})
})
public class Team extends TemporalModel {
    @Id
    private ObjectId _id;
    @Property("projectId")
    private String projectId;
    @Embedded("teamMembers")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team() {}

    public Team(String projectId) {
        this.projectId = projectId;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
