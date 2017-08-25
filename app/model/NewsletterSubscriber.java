package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 30/01/17.
 */
@Entity(name = "newsletter_subscribers")
@Indexes({
        @Index(value = "email_idx", fields = {@org.mongodb.morphia.annotations.Field(value = "email")})
})
public class NewsletterSubscriber extends TemporalModel {
    @Id
    private ObjectId _id;
    @Property("email")
    private String email;
    @Property("isActive")
    public boolean isActive = true;

    public NewsletterSubscriber() {}

    public NewsletterSubscriber(String id, String email, boolean isActive) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.email = email;
        this.isActive = isActive;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return isActive;
    }
}
