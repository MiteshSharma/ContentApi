package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 12/11/16.
 */
@Entity(name = "apps")
@Indexes({
        @Index(value = "app_name_idx", fields = {@Field(value = "name")}),
        @Index(value = "user_id_idx", fields = {@Field(value = "userId")})
})
public class App extends TemporalModel {
    public static final String COLUMN_NAME_APP_NAME = "name";

    @Id
    private ObjectId _id;
    @Property(COLUMN_NAME_APP_NAME)
    private String name;
    @Property("userId")
    private String userId;

    public App() {}

    public App(ObjectId id, String name, String userId) {
        this._id = id;
        this.name = name;
        this.userId = userId;
    }

    public ObjectId getId() {
        return this._id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }
}
