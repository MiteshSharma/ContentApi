package utils;

/**
 * Created by mitesh on 12/11/16.
 */
public class Konstants {
    // application config keys
    public static final String KEY_MASTER_DB_NAME = "mongo.master.name";
    public static final String KEY_MASTER_MAP_PACKAGE = "mongo.master.package";
    public static final String KEY_SHARD1_DB_NAME = "mongo.shard1.name";
    public static final String KEY_MONGO_URI = "mongo.uri";

    // Default values
    public static final String DEFAULT_MASTER_DB_NAME = "content_master";
    public static final String DEFAULT_SHARD1_DB_NAME = "content_shard1";
    public static final String DEFAULT_MONGO_URI = "mongodb://127.0.0.1";
}
