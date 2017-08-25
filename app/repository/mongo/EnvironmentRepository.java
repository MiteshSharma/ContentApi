package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import model.Environment;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import repository.IEnvironmentRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class EnvironmentRepository extends BaseMasterRepository implements IEnvironmentRepository {

    @Inject
    public EnvironmentRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Environment create(Environment environment) {
        if (environment.getId() == null) {
            environment.setId(new ObjectId());
        }
        datastore.save(environment);
        return environment;
    }

    @Override
    public void update(Environment environment) {
        BasicDBObject selectObject = new BasicDBObject("_id", environment.getId());
        BasicDBObject updateData = new BasicDBObject();
        updateData.put(Environment.COLUMN_NAME_ENVIRONMENT_NAME, environment.getName());
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateData);

        DBCollection collection = this.datastore.getCollection(Environment.class);
        collection.update(selectObject, setQuery);
    }

    public Environment get(String environmentId) {
        return getById(Environment.class, environmentId);
    }

    @Override
    public Environment incrementTotalContentCount(Environment environment, int count) {
        BasicDBObject selectObject = new BasicDBObject("_id", environment.getId());
        BasicDBObject incValue = new BasicDBObject("totalContent", count);
        BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

        DBCollection projectDb = this.datastore.getCollection(Environment.class);
        projectDb.update(selectObject, intModifier);

        environment.setTotalContent(environment.getTotalContent() + count);

        return environment;
    }

    public List<Environment> getByProject(String projectId) {
        return getObjects(Environment.class, "projectId", projectId);
    }
}
