package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import repository.ICollectionContentHistoryRepository;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 20/11/16.
 */
public class CollectionContentHistoryRepository extends BaseSlaveRepository implements ICollectionContentHistoryRepository {

    private static final String COLLECTION_HISTORY = "content_history";

    @Inject
    public CollectionContentHistoryRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public boolean create(String collectionName, Document document) {
        MongoCollection<Document> collection = this.nativeStore.getCollection(COLLECTION_HISTORY);
        if (document.getObjectId("_id") != null) {
            return false;
        }
        document.put("_id", new ObjectId());
        document.put("collection_name", collectionName);
        document.put("history_time", System.currentTimeMillis());
        collection.insertOne(document);
        return true;
    }
}
