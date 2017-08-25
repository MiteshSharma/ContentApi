package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public abstract class Validation {
    public abstract boolean isValid(JsonNode node);
}
