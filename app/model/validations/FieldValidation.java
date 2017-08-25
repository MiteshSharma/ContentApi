package model.validations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import model.Field;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by mitesh on 19/11/16.
 */
@Embedded
public class FieldValidation {
    @Embedded("daterangeValidation")
    DateRangeValidation dateRangeValidation;
    @Embedded("fieltypeValidation")
    FileTypeValidation fileTypeValidation;
    @Embedded("imageValidation")
    ImageValidation imageValidation;
    @Embedded("inelementValidation")
    InElementValidation inElementValidation;
    @Embedded("jsonValidation")
    JsonValidation jsonValidation;
    @Embedded("referenceValidation")
    ReferenceValidation referenceValidation;
    @Embedded("regexValidation")
    RegexValidation regexValidation;
    @Embedded("requiredValidation")
    RequiredValidation requiredValidation;
    @Embedded("textLengthValidation")
    TextLengthValidation textLengthValidation;
    @Embedded("numericRangeValidation")
    NumericRangeValidation numericRangeValidation;

    public FieldValidation() {
    }

    public DateRangeValidation getDateRangeValidation() {
        return dateRangeValidation;
    }

    public FileTypeValidation getFileTypeValidation() {
        return fileTypeValidation;
    }

    public ImageValidation getImageValidation() {
        return imageValidation;
    }

    public InElementValidation getInElementValidation() {
        return inElementValidation;
    }

    public JsonValidation getJsonValidation() {
        return jsonValidation;
    }

    public ReferenceValidation getReferenceValidation() {
        return referenceValidation;
    }

    public RegexValidation getRegexValidation() {
        return regexValidation;
    }

    public RequiredValidation getRequiredValidation() {
        return requiredValidation;
    }

    public TextLengthValidation getTextLengthValidation() {
        return textLengthValidation;
    }

    public NumericRangeValidation getNumericRangeValidation() {
        return numericRangeValidation;
    }

    @JsonIgnore
    public boolean validate(Field field, JsonNode content) {
//        if (this.getRequiredValidation() != null && !this.getRequiredValidation().isValid(content)) {
//            return false;
//        }
        switch (field.getFieldType()) {
            case SHORT_TEXT:
            case TEXT:
                return (this.getTextLengthValidation() == null || this.getTextLengthValidation().isValid(content)) &&
                       (this.getRegexValidation() == null || this.getRegexValidation().isValid(content)) &&
                       (this.getInElementValidation() ==  null || this.getInElementValidation().isValid(content));
            case INTEGER:
            case FLOAT:
                return (this.getNumericRangeValidation() == null || this.getNumericRangeValidation().isValid(content)) &&
                       (this.getInElementValidation() ==  null || this.getInElementValidation().isValid(content)) &&
                       (this.getInElementValidation() ==  null || this.getInElementValidation().isValid(content));
            case DATETIME:
                return this.getDateRangeValidation() == null || this.getDateRangeValidation().isValid(content);
            case LOCATION:
                break;
            case FILE:
                return this.getFileTypeValidation() == null || this.getFileTypeValidation().isValid(content);
            case BOOLEAN:
                break;
            case JSON:
                return this.getJsonValidation() == null || this.getJsonValidation().isValid(content);
            case REFERENCE:
                return this.getReferenceValidation() == null || this.getReferenceValidation().isValid(content);
        }
        return true;
    }

}
