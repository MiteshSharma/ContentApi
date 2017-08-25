package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionDetail {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private Date updatedAt;
    private boolean isAuthNeeded;
    List<FieldObject> fieldObjects;

    public CollectionDetail() {}

    public CollectionDetail(String id, String name, String displayName, String description, Date updatedAt, boolean isAuthNeeded) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.updatedAt = updatedAt;
        this.isAuthNeeded = isAuthNeeded;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldObject> getFieldObjects() {
        return fieldObjects;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean isAuthNeeded() {
        return isAuthNeeded;
    }

    public void setFieldObjects(List<FieldObject> fieldObjects) {
        this.fieldObjects = fieldObjects;
    }

    @JsonIgnore
    public Collection getCollectionMetadata(String projectId) {
        return new Collection(this.id, this.name, this.displayName, this.description, projectId, this.isAuthNeeded);
    }

    public static CollectionDetail getCollectionDetail(Collection collection) {
        return new CollectionDetail(collection.getId().toString(), collection.getName(),
                collection.getDisplayName(), collection.getDescription(), collection.getUpdatedAt(), collection.isAuthNeeded());
    }

    public static List<CollectionDetail> getCollectionDetails(List<Collection> collections) {
        List<CollectionDetail> collectionDetails = new ArrayList<>();
        for (Collection collection : collections) {
            collectionDetails.add(getCollectionDetail(collection));
        }
        return collectionDetails;
    }
}
