package exceptions;

/**
 * Created by mitesh on 22/12/16.
 */
public class NoAppExistException extends Exception {
    public NoAppExistException() {
        super("No app exist for this id.");
    }
}
