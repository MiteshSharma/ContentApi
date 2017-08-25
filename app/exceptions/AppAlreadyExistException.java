package exceptions;

/**
 * Created by mitesh on 16/11/16.
 */
public class AppAlreadyExistException extends Exception {

    public AppAlreadyExistException() {
        super("App already exist with this app id.");
    }
}
