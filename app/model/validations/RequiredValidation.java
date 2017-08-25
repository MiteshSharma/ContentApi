package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class RequiredValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;

    public RequiredValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (node != null || !isValidationNeeded) {
            return true;
        }
        return false;
    }
}
