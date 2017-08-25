package exceptions;

/**
 * Created by mitesh on 19/11/16.
 */
public class ContentValidationFailedException extends Exception {
    public ContentValidationFailedException() {
        super("Validation failed for added content");
    }
}
