package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import model.Collection;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import repository.ICollectionRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionRepository extends BaseMasterRepository implements ICollectionRepository {

    @Inject
    public CollectionRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Collection create(Collection collection) {
        if (collection.getId() == null) {
            collection.setId(new ObjectId());
        }
        datastore.save(collection);
        return collection;
    }

    @Override
    public void update(Collection collectionMetadata) {
        BasicDBObject selectObject = new BasicDBObject("_id", collectionMetadata.getId());
        BasicDBObject updateData = new BasicDBObject();
        updateData.put(Collection.COLUMN_NAME_COLLECTION_NAME, collectionMetadata.getName());
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateData);

        DBCollection collection = this.datastore.getCollection(Collection.class);
        collection.update(selectObject, setQuery);
    }

    @Override
    public void updateFieldContent(Collection collection, String displayField, int fieldCount) {
        BasicDBObject selectObject = new BasicDBObject("_id", collection.getId());
        BasicDBObject updateData = new BasicDBObject();
        if (displayField != null) {
            updateData.put("displayField", displayField);
        }
        updateData.put("totalFields", collection.getTotalFields() + fieldCount);
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateData);

        DBCollection collectionDb = this.datastore.getCollection(Collection.class);
        collectionDb.update(selectObject, setQuery);
    }

    @Override
    public Collection incrementTotalContentCount(Collection collection, int count) {
        BasicDBObject selectObject = new BasicDBObject("_id", collection.getId());

        BasicDBObject incValue = new BasicDBObject("totalContent", count);
        BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

        DBCollection projectDb = this.datastore.getCollection(Collection.class);
        projectDb.update(selectObject, intModifier);

        collection.setTotalContent(collection.getTotalContent() + count);

        return collection;
    }

    public Collection get(String collectionId) {
        return getById(Collection.class, collectionId);
    }

    @Override
    public List<Collection> getAll(String projectId) {
        return getObjects(Collection.class, "projectId", projectId);
    }
}
