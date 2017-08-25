package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import model.ProjectApiKey;
import model.User;
import play.Logger;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.IJwtService;
import services.IProjectApiKeyService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mitesh on 22/12/16.
 */
public class ExternalApiAuthManager extends Action<ApiAuth> {
    private static final String ACCESS_TOKEN = "access-token";

    @Inject
    IProjectApiKeyService projectApiKeyService;
    @Inject
    IJwtService jwtService;

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        String authToken = Controller.request().getHeader(ACCESS_TOKEN);
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.session().get(ACCESS_TOKEN);
        }
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.request().getQueryString(ACCESS_TOKEN);
        }

        if (authToken == null) {
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }

        ProjectApiKey projectApiKey = this.projectApiKeyService.get(authToken);

        if (projectApiKey == null) {
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }

        ctx.args.put("projectId", projectApiKey.getProjectId());
        ctx.args.put("environmentId", projectApiKey.getEnvironmentId());

        String userAuthToken = Controller.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (userAuthToken == null || "".equals(userAuthToken)) {
            userAuthToken = Controller.session().get(HttpHeaders.AUTHORIZATION);
        }

        if (userAuthToken != null) {
            String payload = jwtService.verifyJwt(userAuthToken);
            if (payload != null) {
                JsonNode jsonNode = Json.parse(payload);
                String userId = jsonNode.get("userId").asText();
                ctx.args.put("userId", userId);
            }
        }

        return delegate.call(ctx);
    }
}
