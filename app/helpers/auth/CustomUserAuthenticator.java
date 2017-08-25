package helpers.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.HttpAction;

/**
 * Created by mitesh on 09/04/16.
 */
public class CustomUserAuthenticator implements Authenticator<CustomUserCredentials> {

    @Override
    public void validate(CustomUserCredentials credentials, WebContext context) throws HttpAction {
        if (credentials == null) {
            throw new CredentialInvalidException("No credentials provided.");
        }
        if (credentials.getUser() == null) {
            throw new CredentialInvalidException("No user defined for credentials.");
        }
    }
}
