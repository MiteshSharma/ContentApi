package repository;

import com.google.inject.ImplementedBy;
import repository.mongo.CollectionTableRepository;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(CollectionTableRepository.class)
public interface ICollectionTableRepository {
    public void create(String collectionName);
    public void delete(String collectionName);
}
