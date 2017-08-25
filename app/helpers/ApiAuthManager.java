package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import model.ERole;
import model.User;
import org.apache.http.HttpRequest;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import pojo.Scope;
import services.IJwtService;
import services.IUserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mitesh on 28/07/16.
 */
public class ApiAuthManager extends Action<ApiAuth> {

    @Inject
    IJwtService jwtService;
    @Inject
    IUserService userService;

    @Override
    public CompletionStage<Result> call(Http.Context context) {
        Object projectIdObj = Http.Context.current().args.get("projectId");
        if (projectIdObj != null) {
            return delegate.call(context);
        }

        String authToken = Controller.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.session().get(HttpHeaders.AUTHORIZATION);
        }

        if (authToken == null) {
            authToken = Controller.request().getQueryString(HttpHeaders.AUTHORIZATION);
        }

        if (authToken == null) {
            Logger.debug("Auth token not found for call {}", context.request().path());
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }

        String payload = jwtService.verifyJwt(authToken);
        if (payload == null) {
            Logger.debug("Jwt payload coming as null for token {}", authToken);
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }

        JsonNode jsonNode = Json.parse(payload);
        String userId = jsonNode.get("userId").asText();

        User user = this.userService.get(userId);
        if (user == null) {
            Logger.debug("No user exist for userId {}", userId);
            return CompletableFuture.supplyAsync(() -> badRequest());
        }

        context.args.put("userId", userId);

        Scope userProjectScope = ((ApiAuth)configuration).scope();
        if (userProjectScope == Scope.All) {
            return delegate.call(context);
        }
        String projectUserData = jsonNode.get("project").asText();
        JsonNode projectUserJson = Json.parse(projectUserData);
        String projectId = HttpValueParseHelper.getProjectId(context);
        if (projectId == null) {
            return delegate.call(context);
        }
        ERole userRole = ERole.valueOf(projectUserJson.get(projectId).asText());
        if (userRole.getScopes().stream().anyMatch(scope -> (scope == Scope.All || scope == userProjectScope))) {
            if (userRole.getScopes().contains(Scope.GetSelf)) {
                context.args.put("isSelf", true);
            }
            return delegate.call(context);
        } else {
            Logger.debug("No appropriate role provided having role {}", userRole.toString());
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }
    }
}