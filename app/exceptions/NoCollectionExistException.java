package exceptions;

/**
 * Created by mitesh on 19/11/16.
 */
public class NoCollectionExistException extends Exception {

    public NoCollectionExistException() {
        super("No collection identified with this id.");
    }
}
