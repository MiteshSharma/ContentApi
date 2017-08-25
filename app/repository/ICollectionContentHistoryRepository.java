package repository;

import com.google.inject.ImplementedBy;
import org.bson.Document;
import repository.mongo.CollectionContentHistoryRepository;

/**
 * Created by mitesh on 20/11/16.
 */
@ImplementedBy(CollectionContentHistoryRepository.class)
public interface ICollectionContentHistoryRepository {
    public boolean create(String collectionName, Document document);
}
