package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class JsonValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("minKeyCount")
    private long minKeyCount;
    @Property("maxKeyCount")
    private long maxKeyCount;

    public JsonValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public long getMinKeyCount() {
        return minKeyCount;
    }

    public void setMinKeyCount(long minKeyCount) {
        this.minKeyCount = minKeyCount;
    }

    public long getMaxKeyCount() {
        return maxKeyCount;
    }

    public void setMaxKeyCount(long maxKeyCount) {
        this.maxKeyCount = maxKeyCount;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (node == null || !this.isValidationNeeded) {
            return true;
        }
        if (node.size() >= this.minKeyCount && node.size() <= this.maxKeyCount) {
            return true;
        }
        return false;
    }
}
