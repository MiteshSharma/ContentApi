package helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import model.User;
import play.Configuration;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import pojo.ProjectObject;
import services.IJwtService;
import services.IProjectService;
import services.IUserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mitesh on 20/12/16.
 */
public class ProjectAuthManager extends Action<ApiAuth> {

    @Inject
    IJwtService jwtService;
    @Inject
    IProjectService projectService;
    @Inject
    IUserService userService;
    @Inject
    Configuration configuration;

    @Override
    public CompletionStage<Result> call(Http.Context context) {
        String authToken = Controller.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.session().get(HttpHeaders.AUTHORIZATION);
        }
        String path = context.request().path();
        String companyUrlPath = path.replace("/api/v1/project/", "");
        String[] urlParams = companyUrlPath.split("/");
        if (urlParams.length > 0) {
            String projectIdStr = urlParams[0];

            if (authToken == null) {
                return CompletableFuture.supplyAsync(() -> unauthorized());
            }

            String payload = jwtService.verifyJwt(authToken);
            if (payload == null) {
                return CompletableFuture.supplyAsync(() -> unauthorized());
            }

            JsonNode jsonNode = Json.parse(payload);
            String userId = jsonNode.get("userId").asText();

            ProjectObject projectObject = this.projectService.get(projectIdStr, userId);
            if (projectObject == null) {
                return CompletableFuture.supplyAsync(() -> unauthorized());
            }

            User user = userService.get(userId);
            if (configuration.getString("projectId", "1").equals(user.getProjectId()) || projectIdStr.equals(user.getProjectId())) {
                context.args.put("userId", payload);
                return delegate.call(context);
            }
        }
        return CompletableFuture.supplyAsync(() -> unauthorized());
    }
}