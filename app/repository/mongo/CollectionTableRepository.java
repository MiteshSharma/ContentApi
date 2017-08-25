package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import repository.ICollectionTableRepository;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionTableRepository implements ICollectionTableRepository {

    private MongoDatabase nativeStore;

    @Inject
    public CollectionTableRepository(IDbStore dbStore) {
        this.nativeStore = dbStore.getShard();
    }

    @Override
    public void create(String collectionName) {
        this.nativeStore.createCollection(collectionName);
    }

    @Override
    public void delete(String collectionName) {
        MongoCollection collection = this.nativeStore.getCollection(collectionName);
        if (collection != null) {
            collection.drop();
        }
    }
}
