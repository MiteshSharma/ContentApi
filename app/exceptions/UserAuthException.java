package exceptions;

/**
 * Created by mitesh on 17/11/16.
 */
public class UserAuthException extends Exception {
    public UserAuthException() {
        super("User auth failed");
    }
}
