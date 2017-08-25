package repository.mongo.driver;

import com.google.inject.ImplementedBy;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(DbStore.class)
public interface IDbStore {
    public Datastore getMaster();
    public MongoDatabase getShard();
}
