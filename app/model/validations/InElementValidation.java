package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class InElementValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("elements")
    private List<String> elements;

    public InElementValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (node == null || !this.isValidationNeeded) {
            return true;
        }
        String content = node.asText();
        if (this.elements.contains(content)) {
            return true;
        }
        return false;
    }
}
