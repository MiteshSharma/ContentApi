package model.validations;

import com.fasterxml.jackson.databind.JsonNode;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mitesh on 12/11/16.
 */
@Embedded
public class RegexValidation extends Validation {
    @Property("isValidationNeeded")
    private boolean isValidationNeeded;
    @Property("regex")
    private String regex;

    public RegexValidation() {}

    public boolean getIsValidationNeeded() {
        return isValidationNeeded;
    }

    public void setIsValidationNeeded(boolean isValidationNeeded) {
        this.isValidationNeeded = isValidationNeeded;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean isValid(JsonNode node) {
        if (node == null || !this.isValidationNeeded) {
            return true;
        }
        String content = node.asText();
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
