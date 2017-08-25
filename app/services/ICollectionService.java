package services;

import com.google.inject.ImplementedBy;
import exceptions.NoCollectionExistException;
import exceptions.NoProjectExistException;
import model.Collection;
import model.Field;
import model.Project;
import pojo.CollectionDetail;
import services.impl.CollectionService;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(CollectionService.class)
public interface ICollectionService {
    public CollectionDetail create(String projectId, Collection collection) throws NoProjectExistException;
    public CollectionDetail createFields(String projectId, String collectionId, List<Field> fields) throws NoProjectExistException, NoCollectionExistException;
    public CollectionDetail updateField(String projectId, String collectionId, Field field) throws NoProjectExistException, NoCollectionExistException;
    public CollectionDetail deleteField(String projectId, String collectionId, String fieldId) throws NoProjectExistException, NoCollectionExistException;
    public CollectionDetail get(String projectId, String collectionId);
    public List<CollectionDetail> getAll(String projectId);
}