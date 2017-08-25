package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import model.User;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import repository.IUserRepository;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 16/11/16.
 */
public class UserRepository implements IUserRepository {
    Datastore datastore;

    @Inject
    public UserRepository(IDbStore dbStore) {
        this.datastore = dbStore.getMaster();
    }

    @Override
    public User save(User user) {
        User oldUser = this.get(user.getEmail(), user.getProjectId());
        if (oldUser == null) {
            if (user.getPassword() != null) {
                user.setSaltApplied(BCrypt.gensalt());
                user.setPasswordHash(BCrypt.hashpw(user.getPassword(), user.getSaltApplied()));
            }
            user = this.create(user);
        } else {
            oldUser.setName(user.getName());
            datastore.save(oldUser);
            user = oldUser;
        }
        return user;
    }

    @Override
    public User create(User user) {
        if (user.getId() == null) {
            user.setId(new ObjectId());
        }
        datastore.save(user);
        return user;
    }

    @Override
    public User get(String email, String projectId) {
        Query<User> response =  this.datastore.createQuery(User.class).field("email").equal(email).field("projectId").equal(projectId);
        return response.get();
    }

    @Override
    public User get(String email) {
        Query<User> response =  this.datastore.find(User.class, "email",
                email).limit(1);
        return response.get();
    }

    @Override
    public User getById(String id) {
        Query<User> response =  this.datastore.find(User.class, "_id",
                new ObjectId(id)).limit(1);
        return response.get();
    }

    @Override
    public boolean isExist(String email, String projectId) {
        Query<User> response = this.datastore.createQuery(User.class).field("email").equal(email).field("projectId").equal(projectId);
        if (response.getBatchSize() > 0) {
            return true;
        }
        return false;
    }
}
