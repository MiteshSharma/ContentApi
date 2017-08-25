package helpers.auth;

import org.pac4j.core.client.DirectClientV2;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.ProfileCreator;
import redis.clients.jedis.JedisPool;

/**
 * Created by mitesh on 09/04/16.
 */
public class CustomUserClient extends DirectClientV2<CustomUserCredentials, CommonProfile> {

    public CustomUserClient() {

    }

    @Override
    protected void internalInit(final WebContext context) {
        setCredentialsExtractor(new CustomClientExtractor());
    }

    public CustomUserClient(Authenticator authenticator, ProfileCreator profileCreator) {
        setAuthenticator(authenticator);
        setProfileCreator(profileCreator);
    }

//
//    @Override
//    protected BaseClient<CustomUserCredentials, HttpProfile> newClient() {
//        CustomUserClient customUserClient = new CustomUserClient();
//        customUserClient.setRedirectUrl(this.redirectUrl);
//        return customUserClient;
//    }

//    @Override
//    protected boolean isDirectRedirection() {
//        return true;
//    }
//
//    @Override
//    protected RedirectAction retrieveRedirectAction(WebContext webContext) {
//        return RedirectAction.redirect(this.redirectUrl);
//    }


    public static Long getUserId(String token, JedisPool jedisPool) {
        long userId = -1;
//        try {
//            Jedis jedis = jedisPool.getResource();
//            String userIdStr = jedis.hget("Session", token);
//            if (userIdStr != null) {
//                userId = Long.parseLong(userIdStr);
//            }
//            jedisPool.returnResource(jedis);
//        } catch (Exception ex) {
//            UserSession userSession = UserSession.getSession(token);
//            if (userSession != null) {
//                userId = userSession.userId;
//                try {
//                    Jedis jedis = jedisPool.getResource();
//                    jedis.hset("Session", userSession.session, userSession.userId+"");
//                    jedisPool.returnResource(jedis);
//                } catch (Exception e) {
//                }
//            }
//        }
        return userId;
    }

//    @Override
//    public Mechanism getMechanism() {
//        return Mechanism.FORM_MECHANISM;
//    }
}
