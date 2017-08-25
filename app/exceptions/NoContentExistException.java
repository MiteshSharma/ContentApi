package exceptions;

/**
 * Created by mitesh on 20/11/16.
 */
public class NoContentExistException extends Exception {
    public NoContentExistException() {
        super("No content found with given id.");
    }
}
