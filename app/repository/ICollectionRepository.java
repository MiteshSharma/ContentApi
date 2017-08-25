package repository;

import com.google.inject.ImplementedBy;
import model.Collection;
import repository.mongo.CollectionRepository;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(CollectionRepository.class)
public interface ICollectionRepository {
    public Collection create(Collection collection);
    public void update(Collection collection);
    public void updateFieldContent(Collection collection, String displayField, int fieldCount);
    public Collection incrementTotalContentCount(Collection collection, int count);
    public Collection get(String collectionId);
    public List<Collection> getAll(String projectId);
}
