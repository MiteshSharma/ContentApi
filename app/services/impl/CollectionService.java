package services.impl;

import com.google.inject.Inject;
import com.mongodb.client.model.IndexOptions;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoCollectionExistException;
import exceptions.NoProjectExistException;
import model.Collection;
import model.Field;
import model.FieldType;
import model.Project;
import pojo.CollectionDetail;
import pojo.FieldObject;
import repository.*;
import services.ICollectionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionService implements ICollectionService {

    @Inject
    ICollectionRepository collectionRepository;
    @Inject
    ICollectionTableRepository collectionDataRepository;
    @Inject
    ICollectionIndexRepository collectionIndexRepository;
    @Inject
    IFieldRepository fieldRepository;
    @Inject
    IProjectRepository projectRepository;

    @Override
    public CollectionDetail create(String projectId, Collection collection) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        collection = this.collectionRepository.create(collection);
        this.collectionDataRepository.create("CMS"+collection.getId().toString());

        this.projectRepository.incrementCollectionCount(project, 1);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("collectionName", collection.getName());
        EventDispatcher.dispatch(EventName.COLLECTION_CREATE, eventParam);

        return CollectionDetail.getCollectionDetail(collection);
    }

    @Override
    public CollectionDetail createFields(String projectId, String collectionId, List<Field> fields) throws NoProjectExistException, NoCollectionExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        String displayField = null;
        for (Field field : fields) {
            this.fieldRepository.create(field);
            if (field.getIsDisplayFlag()) {
                displayField = field.getDisplayName();
            }
            if (field.getIsIndexable() || field.getIsUnique()) {
                IndexOptions indexOptions = new IndexOptions().background(true);
                if (field.getIsUnique()) {
                    indexOptions = indexOptions.unique(true);
                }
                if (field.getFieldType().equals(FieldType.LOCATION)) {
                    indexOptions = indexOptions.sparse(true);
                }
                this.collectionIndexRepository.create("CMS"+collection.getId().toString(), field.getName(), indexOptions);
            }

            Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
            eventParam.put("projectId", project.getId().toString());
            eventParam.put("projectName", project.getName());
            eventParam.put("collectionName", collection.getName());
            eventParam.put("fieldName", field.getName());
            EventDispatcher.dispatch(EventName.FIELD_CREATE, eventParam);
        }
        this.collectionRepository.updateFieldContent(collection, displayField, fields.size());

        return getCollectionDetail(collection);
    }

    @Override
    public CollectionDetail updateField(String projectId, String collectionId, Field field) throws NoProjectExistException, NoCollectionExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        Field oldField = fieldRepository.get(field.getId().toString());
        if (oldField.getIsUnique() || oldField.getIsIndexable()) {
            if (!(field.getIsUnique() || field.getIsIndexable())) {
                this.collectionIndexRepository.drop("CMS"+collection.getId().toString(), oldField.getName());
            }
        } else {
            if (field.getIsUnique() || field.getIsIndexable()) {
                IndexOptions indexOptions = new IndexOptions().background(true);
                if (field.getIsUnique()) {
                    indexOptions = indexOptions.unique(true);
                }
                if (field.getFieldType().equals(FieldType.LOCATION)) {
                    indexOptions = indexOptions.sparse(true);
                }
                this.collectionIndexRepository.create("CMS"+collection.getId().toString(), field.getName(), indexOptions);
            }
        }
        this.fieldRepository.update(field);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("collectionName", collection.getName());
        eventParam.put("fieldName", field.getName());
        EventDispatcher.dispatch(EventName.FIELD_UPDATE, eventParam);
        return getCollectionDetail(collection);
    }

    @Override
    public CollectionDetail deleteField(String projectId, String collectionId, String fieldId) throws NoProjectExistException, NoCollectionExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        Field field = this.fieldRepository.get(fieldId);
        this.fieldRepository.delete(fieldId);
        this.collectionRepository.updateFieldContent(collection, null, -1);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("collectionName", collection.getName());
        eventParam.put("fieldName", field.getName());
        EventDispatcher.dispatch(EventName.FIELD_DELETE, eventParam);
        return getCollectionDetail(collection);
    }

    @Override
    public CollectionDetail get(String projectId, String collectionId) {
        Collection collection = this.collectionRepository.get(collectionId);
        return getCollectionDetail(collection);
    }

    private CollectionDetail getCollectionDetail(Collection collection) {
        List<Field> fields = this.fieldRepository.getAll(collection.getId().toString());
        List<FieldObject> fieldObjects = FieldObject.getFieldObjects(fields);
        CollectionDetail collectionDetail = CollectionDetail.getCollectionDetail(collection);
        collectionDetail.setFieldObjects(fieldObjects);
        return collectionDetail;
    }

    @Override
    public List<CollectionDetail> getAll(String projectId) {
        List<Collection> collections = this.collectionRepository.getAll(projectId);
        List<CollectionDetail> collectionDetails = new ArrayList<>();
        for (Collection collection : collections) {
            collectionDetails.add(getCollectionDetail(collection));
        }
        return collectionDetails;
    }
}