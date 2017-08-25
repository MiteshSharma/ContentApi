package repository.mongo;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import repository.mongo.driver.IDbStore;
import org.bson.Document;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by mitesh on 19/11/16.
 */
public class BaseSlaveRepository {
    protected MongoDatabase nativeStore;

    @Inject
    public BaseSlaveRepository(IDbStore dbStore) {
        this.nativeStore = dbStore.getShard();
    }

    protected Document getDocumentFromJsonNode(JsonNode jsonNode) {
        Document doc = new Document();
        if (jsonNode.get("_id") == null) {
            doc.append("_id", new ObjectId());
        }
        Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            if (entry.getValue() != null) {
                doc.append(entry.getKey(), entry.getValue().asText());
            }
        }
        return doc;
    }
}
