package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by mitesh on 16/11/16.
 */
public class TemporalModel {
    @JsonIgnore
    @Property("createdAt")
    public Date createdAt;
    @JsonIgnore
    @Property("updatedAt")
    public Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = (createdAt == null) ? new Date() : createdAt;
        updatedAt = (updatedAt == null) ? createdAt : new Date();
    }
}
