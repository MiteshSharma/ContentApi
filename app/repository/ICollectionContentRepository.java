package repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import org.bson.Document;
import repository.mongo.AlternateCollectionContentRepository;
import repository.mongo.CollectionContentRepository;

import java.util.List;

/**
 * Created by mitesh on 19/11/16.
 */
@ImplementedBy(CollectionContentRepository.class)
public interface ICollectionContentRepository {
    public String create(String collectionName, JsonNode jsonNode);
    public String update(String collectionName, String contentId, JsonNode jsonNode);
    public String getByEnv(String collectionName, String envId, String userId,
                           int offset, int limit, String sortBy, String orderBy, int isCountNeeded, String filters);
    public String getById(String collectionName, String contentId);
    public Document getByIdWithDocument(String collectionName, String contentId);
}
