package repository.mongo;

import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 18/11/16.
 */
public class BaseMasterRepository {

    Datastore datastore;

    @Inject
    public BaseMasterRepository(IDbStore dbStore) {
        this.datastore = dbStore.getMaster();
    }

    public <T> T getById(Class<T> className, String id) {
        return this.datastore.createQuery(className).field("_id").equal(new ObjectId(id)).get();
    }

    public <T> List<T> getObjects(Class<T> className, String fieldName, Object value) {
        return this.datastore.createQuery(className).field(fieldName).equal(value).asList();
    }

    public <T> T getObject(Class<T> className, String fieldName, Object value) {
        return this.datastore.createQuery(className).field(fieldName).equal(value).get();
    }
}
