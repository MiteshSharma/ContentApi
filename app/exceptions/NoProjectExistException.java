package exceptions;

/**
 * Created by mitesh on 19/11/16.
 */
public class NoProjectExistException extends Exception {
    public NoProjectExistException() {
        super("No project exist for this id.");
    }
}
