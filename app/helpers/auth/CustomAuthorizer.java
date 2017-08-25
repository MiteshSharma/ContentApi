package helpers.auth;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;

import java.util.List;

/**
 * Created by mitesh on 17/11/16.
 */
public class CustomAuthorizer extends ProfileAuthorizer<CustomUserProfile> {

    @Override
    public boolean isAuthorized(final WebContext context, final List<CustomUserProfile> profiles) throws HttpAction {
        return isAnyAuthorized(context, profiles);
    }

    @Override
    public boolean isProfileAuthorized(final WebContext context, final CustomUserProfile profile) {
        if (profile == null) {
            return false;
        }
        return StringUtils.startsWith(profile.getUsername(), "jle");
    }
}