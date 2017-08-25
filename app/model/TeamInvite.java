package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 05/01/17.
 */
@Entity(name = "team_invites")
@Indexes({
        @Index(value = "project_id_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "projectId")})
})
public class TeamInvite extends TemporalModel {
    @Id
    private ObjectId _id;
    @Property("projectId")
    private String projectId;
    @Property("role")
    private ERole role;
    @Property("email")
    private String email;

    public TeamInvite() {}

    public TeamInvite(String id, String projectId, String email, ERole role) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.projectId = projectId;
        this.email = email;
        this.role = role;
    }

    public ObjectId getId() {
        return this._id;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public ERole getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }
}
