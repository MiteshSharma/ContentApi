package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import repository.ICollectionIndexRepository;
import repository.mongo.driver.IDbStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionIndexRepository implements ICollectionIndexRepository {

    private MongoDatabase nativeStore;

    @Inject
    public CollectionIndexRepository(IDbStore dbStore) {
        this.nativeStore = dbStore.getShard();
    }

    @Override
    public void create(String collectionName, String indexProperty, IndexOptions indexOptions) {
        MongoCollection collection = this.nativeStore.getCollection(collectionName);
        if (collection != null) {
            collection.createIndex(new BasicDBObject(indexProperty, 1), indexOptions);
        }
    }

    @Override
    public List<String> get(String collectionName) {
        List<String> indexFieldNames = new ArrayList<>();
        MongoCollection collection = this.nativeStore.getCollection(collectionName);
        if (collection != null) {
            ListIndexesIterable<Document> indexes = collection.listIndexes();
            for (Document index : indexes) {
                indexFieldNames.add(index.getString("name"));
            }
        }
        return indexFieldNames;
    }

    @Override
    public void drop(String collectionName) {
        MongoCollection collection = this.nativeStore.getCollection(collectionName);
        if (collection != null) {
            ListIndexesIterable<Document> indexes = collection.listIndexes();
            for (Document index : indexes) {
                collection.dropIndex(index);
            }
        }
    }

    @Override
    public void drop(String collectionName, String indexProperty) {
        MongoCollection collection = this.nativeStore.getCollection(collectionName);
        if (collection != null) {
            collection.dropIndex(new BasicDBObject(indexProperty, 1));
        }
    }
}