package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.validations.FieldValidation;
import model.validations.Validation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mitesh on 12/11/16.
 */
@Entity(name = "fields")
public class Field {
    public static final String COLUMN_NAME_FIELD_NAME = "name";
    @Id
    private ObjectId _id;
    @Property(COLUMN_NAME_FIELD_NAME)
    private String name;
    @Property("displayName")
    private String displayName;
    @Property("collectionId")
    private String collectionId;
    @Property("fieldType")
    private FieldType fieldType;
    @Property("isLocalized")
    private boolean isLocalized;
    @Property("isAnalytic")
    private boolean isAnalytic;
    @Property("isOmitted")
    private boolean isOmitted;
    @Property("isUnique")
    private boolean isUnique;
    @Property("isIndexable")
    private boolean isIndexable;
    @Property("isDisplayFlag")
    private boolean isDisplayFlag;

    @Embedded("validations")
    private FieldValidation validations;

    public Field() {}

    public Field(String id, String name, String displayName, String collectionId, String fieldType,
                 boolean isLocalized, boolean isAnalytic, boolean isOmitted, boolean isUnique, boolean isIndexable,
                 boolean isDisplayFlag, FieldValidation validations) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.name = name;
        this.displayName = displayName;
        this.collectionId = collectionId;
        this.fieldType = FieldType.valueOf(fieldType);
        this.isLocalized = isLocalized;
        this.isAnalytic = isAnalytic;
        this.isOmitted = isOmitted;
        this.isUnique = isUnique;
        this.isIndexable = isIndexable;
        this.isDisplayFlag = isDisplayFlag;
        this.validations = validations;
    }

    @JsonIgnore
    public ObjectId getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public boolean getIsLocalized() {
        return isLocalized;
    }

    public boolean getIsAnalytic() {
        return isAnalytic;
    }

    public boolean getIsOmitted() {
        return isOmitted;
    }

    public boolean getIsUnique() {
        return isUnique;
    }

    public boolean getIsIndexable() {
        return isIndexable;
    }

    public boolean getIsDisplayFlag() {
        return isDisplayFlag;
    }

    public FieldValidation getValidations() {
        return validations;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setIsLocalized(boolean localized) {
        isLocalized = localized;
    }

    public void setIsOmitted(boolean omitted) {
        isOmitted = omitted;
    }

    public void setIsUnique(boolean unique) {
        isUnique = unique;
    }

    public void setIsIndexable(boolean indexable) {
        isIndexable = indexable;
    }
}
