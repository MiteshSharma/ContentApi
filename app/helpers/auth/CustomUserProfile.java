package helpers.auth;

import org.pac4j.core.profile.CommonProfile;

/**
 * Created by mitesh on 17/11/16.
 */
public class CustomUserProfile extends CommonProfile {

    public CustomUserProfile(CustomUserCredentials credentials) {
        this.addAttribute("email", credentials.getUser().getEmail());
    }
}
