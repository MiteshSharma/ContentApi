package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class DateRangeValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("minDate")
    private Date minDate;
    @Property("maxDate")
    private Date maxDate;

    public DateRangeValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (!this.isValidationNeeded ) {
            return true;
        }
        Date date = new Date(node.asLong());
        if ((date.after(this.minDate) && date.before(this.maxDate))) {
            return true;
        }
        return false;
    }
}
