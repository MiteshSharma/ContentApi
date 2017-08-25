package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by mitesh on 19/11/16.
 */
@Embedded
public class NumericRangeValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("minValue")
    private long minValue;
    @Property("maxValue")
    private long maxValue;

    public NumericRangeValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public long getMinValue() {
        return minValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (node == null || !this.isValidationNeeded) {
            return true;
        }
        long content = node.asLong();
        if (content >= this.minValue && content <= this.maxValue) {
            return true;
        }
        return false;
    }
}
