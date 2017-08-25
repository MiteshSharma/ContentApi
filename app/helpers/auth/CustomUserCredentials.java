package helpers.auth;

import model.User;
import org.pac4j.core.credentials.Credentials;

/**
 * Created by mitesh on 09/04/16.
 */
public class CustomUserCredentials extends Credentials {

    private User user;

    public CustomUserCredentials(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public String getUserName() {
        return this.user.getName();
    }

    public String getUserId() {
        if (this.user != null) {
            return this.user.getId().toString();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
