package helpers.auth;

import org.pac4j.core.exception.TechnicalException;

/**
 * Created by mitesh on 10/04/16.
 */
public class CredentialInvalidException extends TechnicalException {
    public CredentialInvalidException(String message) {
        super(message);
    }
}
