package exceptions;

/**
 * Created by mitesh on 16/11/16.
 */
public class AppNameExistException extends Exception {
    public AppNameExistException() {
        super("App name exists, can't create another app with same name.");
    }
}
