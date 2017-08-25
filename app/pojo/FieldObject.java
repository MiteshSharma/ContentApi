package pojo;

import com.google.inject.Inject;
import model.Field;
import model.validations.FieldValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 19/11/16.
 */
public class FieldObject {
    private String id;
    private String name;
    private String displayName;
    private String fieldType;
    private boolean isLocalized;
    private boolean isAnalytic;
    private boolean isOmitted;
    private boolean isUnique;
    private boolean isIndexable;
    private boolean isDisplayFlag;

    private FieldValidation validations;

    public FieldObject() {}

    public FieldObject(String id, String name, String displayName, String fieldType,
                 boolean isLocalized, boolean isAnalytic, boolean isOmitted, boolean isUnique, boolean isIndexable,
                       boolean isDisplayFlag, FieldValidation validations) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.fieldType = fieldType;
        this.isLocalized = isLocalized;
        this.isOmitted = isOmitted;
        this.isUnique = isUnique;
        this.isIndexable = isIndexable;
        this.isDisplayFlag = isDisplayFlag;
        this.validations = validations;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public boolean getIsLocalized() {
        return isLocalized;
    }

    public boolean getIsAnalutic() {
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

    @Inject
    public Field getField(String collectionId) {
        return new Field(this.id, this.name, this.displayName, collectionId, this.fieldType,
                this.isLocalized, this.isAnalytic, this.isOmitted, this.isUnique, this.isIndexable,
                this.isDisplayFlag, this.validations);
    }

    public static FieldObject getFieldObject(Field field) {
        return new FieldObject(field.getId().toString(), field.getName(), field.getDisplayName(),
                field.getFieldType().name(), field.getIsLocalized(), field.getIsAnalytic(), field.getIsOmitted(), field.getIsUnique(),
                field.getIsIndexable(), field.getIsDisplayFlag(), field.getValidations());
    }

    public static List<FieldObject> getFieldObjects(List<Field> fields) {
        List<FieldObject> fieldObjects = new ArrayList<>();
        for (Field field : fields) {
            fieldObjects.add(getFieldObject(field));
        }
        return fieldObjects;
    }

    public static List<Field> getFields(String collectionId, List<FieldObject> fieldObjects) {
        List<Field> fields = new ArrayList<>();
        for (FieldObject fieldObject : fieldObjects) {
            fields.add(fieldObject.getField(collectionId));
        }
        return fields;
    }
}
