package exceptions;

/**
 * Created by mitesh on 19/11/16.
 */
public class NoEnvironmentExistException extends Exception {

    public NoEnvironmentExistException() {
        super("No environment exist with given id.");
    }
}
