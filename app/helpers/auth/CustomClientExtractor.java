package helpers.auth;

import com.google.common.net.HttpHeaders;
import com.google.inject.Guice;
import com.google.inject.Inject;
import model.User;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;
import org.pac4j.core.exception.HttpAction;
import play.mvc.Controller;
import services.IUserService;

/**
 * Created by mitesh on 17/11/16.
 */
public class CustomClientExtractor implements CredentialsExtractor<CustomUserCredentials> {

    @Inject
    IUserService userService;
    @Override
    public CustomUserCredentials extract(WebContext context) throws HttpAction {
        String authToken = Controller.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.session().get(HttpHeaders.AUTHORIZATION);
        }

        userService = Guice.createInjector().getInstance(IUserService.class);

        User user = userService.get(authToken);
        return new CustomUserCredentials(user);
    }
}
