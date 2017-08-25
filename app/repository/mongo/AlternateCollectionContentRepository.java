package repository.mongo;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.types.ObjectId;
import play.libs.Json;
import repository.ICollectionContentRepository;
import repository.mongo.driver.IDbStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 27/12/16.
 */
public class AlternateCollectionContentRepository extends BaseSlaveRepository implements ICollectionContentRepository {

    private static final String COLLECTION_NAME = "collection_contents";

    @Inject
    public AlternateCollectionContentRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public String create(String collectionName, JsonNode jsonNode) {
        MongoCollection<Document> collection = this.nativeStore.getCollection(COLLECTION_NAME);
        Document document = getDocumentFromJsonNode(jsonNode);
        document.put("collectionName", collectionName);
        collection.insertOne(document);
        return document.toJson();
    }

    @Override
    public String update(String collectionName, String contentId, JsonNode jsonNode) {
        MongoCollection<Document> collection = this.nativeStore.getCollection(COLLECTION_NAME);
        BasicDBObject filterQuery = new BasicDBObject();
        filterQuery.put("_id", contentId);
        filterQuery.put("collectionName", collectionName);
        BasicDBObject updateParams = new BasicDBObject();
        Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = nodes.next();
            if (entry.getKey().equals("id") || entry.getKey().equals("envId") || entry.getKey().equals("collectionName")) {
                continue;
            }
            if (entry.getValue() != null) {
                updateParams.put(entry.getKey(), entry.getValue().asText());
            }
        }
        Document document = collection.findOneAndUpdate(filterQuery, updateParams);
        return document.toJson();
    }

    @Override
    public String getByEnv(String collectionName, String envId, String userId, int offset,
                           int limit, String sortBy, String orderBy, int isCountNeeded, String filters) {

        List<Document> responseJson = new ArrayList<>();
        MongoCollection<Document> collection = this.nativeStore.getCollection(COLLECTION_NAME);

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("envId", envId);
        whereQuery.put("collectionName", collectionName);
        if (userId != null && !"".equals(userId)) {
            whereQuery.put("userId", userId);
        }

        if (isCountNeeded == 1) {
            CountOptions countOptions = new CountOptions();
            if (offset != -1) {
                countOptions.skip(offset);
            }
            if (limit != -1) {
                countOptions.limit(limit);
            }
            long count = collection.count(whereQuery);
            return "{'count': "+count+"}";
        } else {
            FindIterable<Document> iterable = collection.find(whereQuery);
            if (offset != -1) {
                iterable.skip(offset);
            }
            if (limit != -1) {
                iterable.limit(limit);
            }
            if (filters != null && !"".equals(filters)) {
                String[] filterArr = filters.split(",");
                iterable.projection(Projections.include(filterArr));
            }
            if (sortBy != null) {
                BasicDBObject sortObject = new BasicDBObject();
                int isAsc = 1;
                if (orderBy != null) {
                    isAsc = orderBy.equals("ASC") ? 1 : -1;
                }
                sortObject.put(sortBy, isAsc);
                iterable.sort(sortObject);
            }
            iterable.forEach(new Block<Document>() {
                public void apply(final Document document) {
                    document.remove("envId");
                    document.remove("userId");
                    document.remove("collectionName");
                    document.remove("contentState");
                    document.remove("contentVersion");
                    ObjectId _id = document.getObjectId("_id");
                    document.remove("_id");
                    document.put("id", _id.toString());
                    responseJson.add(document);
                }
            });
            return Json.stringify(Json.toJson(responseJson));
        }
    }

    @Override
    public String getById(String collectionName, String contentId) {
        Document document = getByIdWithDocument(collectionName, contentId);
        if (document != null) {
            return document.toJson();
        }
        return "";
    }

    @Override
    public Document getByIdWithDocument(String collectionName, String contentId) {
        List<Document> responseDocuments = new ArrayList<>();
        MongoCollection<Document> collection = this.nativeStore.getCollection(COLLECTION_NAME);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", new ObjectId(contentId));
        whereQuery.put("collectionName", collectionName);
        FindIterable<Document> iterable = collection.find(whereQuery);
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                document.remove("envId");
                document.remove("userId");
                document.remove("collectionName");
                document.remove("contentState");
                document.remove("contentVersion");
                ObjectId _id = document.getObjectId("_id");
                document.remove("_id");
                document.put("id", _id.toString());
                responseDocuments.add(document);
            }
        });
        if (responseDocuments.size() > 0) {
            return responseDocuments.get(0);
        }
        return null;
    }
}