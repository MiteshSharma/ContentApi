package helpers.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.creator.ProfileCreator;

/**
 * Created by mitesh on 10/04/16.
 */
public class CustomProfileCreator implements ProfileCreator<CustomUserCredentials, CustomUserProfile> {

    @Override
    public CustomUserProfile create(CustomUserCredentials credentials, WebContext context) throws HttpAction {
        return new CustomUserProfile(credentials);
    }
}
