package exceptions;

/**
 * Created by mitesh on 16/11/16.
 */
public class UserLoginFailedException extends Exception {

    public UserLoginFailedException() {
        super("User login failed.");
    }
}
