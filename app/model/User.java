package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by mitesh on 14/11/16.
 */
@Entity(name = "users")
@Indexes({
        @Index(value = "email_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "email")}),
        @Index(value = "email_app_idx", fields = { @org.mongodb.morphia.annotations.Field(value = "email"), @org.mongodb.morphia.annotations.Field(value = "appId")})
})
public class User extends TemporalModel {
    @Id
    private ObjectId _id;
    @Property("name")
    private String name;
    @Property("email")
    private String email;
    @JsonIgnore
    @Property("saltApplied")
    private String saltApplied;
    @JsonIgnore
    @Property("passwordHash")
    private String passwordHash;
    @JsonIgnore
    @Property("supersedePassword")
    private String supersedePassword;
    @JsonIgnore
    @Property("projectId")
    private String projectId;
    @Property("type")
    private String type;
    @Property("externalId")
    private String externalId;

    @Transient
    @JsonIgnore
    private String password;

    @JsonIgnore
    public ObjectId getId() {
        return this._id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getType() {
        return type;
    }

    public String getSaltApplied() {
        return saltApplied;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSupersedePassword() {
        return supersedePassword;
    }

    public String getPassword() {
        return password;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSaltApplied(String saltApplied) {
        this.saltApplied = saltApplied;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSupersedePassword(String supersedePassword) {
        this.supersedePassword = supersedePassword;
    }

    @JsonIgnore
    public boolean isValid() {
        if (this.name == null || this.email == null || this.type == null) {
            return false;
        }
        return true;
    }
}
