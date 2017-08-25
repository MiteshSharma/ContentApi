package helpers.auth;

import com.google.inject.AbstractModule;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.play.store.PlaySessionStore;

/**
 * Created by mitesh on 09/04/16.
 */
public class AuthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlaySessionStore.class).to(PlayCacheStore.class);
        CustomUserClient customUserClient = new CustomUserClient(new CustomUserAuthenticator(), new CustomProfileCreator());

        Clients clients = new Clients(customUserClient);
        Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
        config.setHttpActionAdapter(new CustomHttpActionAdapter());
        bind(Config.class).toInstance(config);
    }
}