package helpers.auth;

/**
 * Created by mitesh on 10/04/16.
 */
public class SessionExpireException extends Exception {

    public SessionExpireException(String message) {
        super(message);
    }
}
