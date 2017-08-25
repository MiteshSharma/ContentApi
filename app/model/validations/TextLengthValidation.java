package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class TextLengthValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("minLength")
    private long minLength;
    @Property("maxLength")
    private long maxLength;

    public TextLengthValidation() {
    }

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public long getMinLength() {
        return minLength;
    }

    public void setMinLength(long minLength) {
        this.minLength = minLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (!this.isValidationNeeded) {
            return true;
        }
        String content = node.asText();
        if (content != null && content.length() >= this.minLength && content.length() <= this.maxLength) {
            return true;
        } else {
            if (this.minLength == 0) {
                return true;
            }
        }
        return false;
    }
}
