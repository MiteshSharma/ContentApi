package repository;

import com.google.inject.ImplementedBy;
import com.mongodb.client.model.IndexOptions;
import repository.mongo.CollectionIndexRepository;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(CollectionIndexRepository.class)
public interface ICollectionIndexRepository {
    public void create(String collectionName, String indexProperty, IndexOptions indexOptions);
    public List<String> get(String collectionName);
    public void drop(String collectionName, String indexProperty);
    public void drop(String collectionName);
}
