package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class ImageValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("minSize")
    private long minSize;
    @Property("maxSize")
    private long maxSize;

    public ImageValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public long getMinSize() {
        return minSize;
    }

    public void setMinSize(long minSize) {
        this.minSize = minSize;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (!this.isValidationNeeded) {
            return true;
        }
        return true;
    }
}
