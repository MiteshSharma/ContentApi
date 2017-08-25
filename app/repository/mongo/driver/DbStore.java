package repository.mongo.driver;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.Configuration;
import utils.Konstants;

/**
 * Created by mitesh on 12/11/16.
 */
public class DbStore implements IDbStore {
    private Morphia morphia = null;
    private Datastore morphiaStore = null;
    private MongoDatabase nativeStore;

    Configuration configuration;

    @Inject
    public DbStore(Configuration _configuration) {
        this.configuration = _configuration;
        morphia = new Morphia();
    }

    public Datastore getMaster() {
        if (morphiaStore == null) {
            synchronized (DbStore.class) {
                if (morphiaStore == null) {
                    String masterDbName = configuration.getString(Konstants.KEY_MASTER_DB_NAME, Konstants.DEFAULT_MASTER_DB_NAME);
                    String mapMorphiaPackage = configuration.getString(Konstants.KEY_MASTER_MAP_PACKAGE);
                    if (mapMorphiaPackage != null) {
                        morphia.mapPackage(mapMorphiaPackage);
                    }
                    morphiaStore = morphia.createDatastore(getMongoClient(), masterDbName);
                    morphiaStore.ensureIndexes();
                }
            }
        }
        return morphiaStore;
    }

    public MongoDatabase getShard() {
        if (nativeStore == null) {
            synchronized (DbStore.class) {
                if (nativeStore == null) {
                    String shard1DbName = configuration.getString(Konstants.KEY_SHARD1_DB_NAME, Konstants.DEFAULT_SHARD1_DB_NAME);
                    nativeStore = getMongoClient().getDatabase(shard1DbName);
                }
            }
        }
        return nativeStore;
    }

    private MongoClient getMongoClient() {
        String mongoURI = configuration.getString(Konstants.KEY_MONGO_URI, Konstants.DEFAULT_MONGO_URI);
        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        return mongoClient;
    }
}
