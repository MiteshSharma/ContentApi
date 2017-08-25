package controllers;

import com.google.inject.Inject;
import exceptions.NoProjectExistException;
import helpers.ApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.EnvironmentObject;
import pojo.Scope;
import services.IEnvironmentService;

/**
 * Created by mitesh on 18/11/16.
 */
public class EnvironmentController extends CoreController {
    @Inject
    IEnvironmentService environmentService;

    @ApiAuth(scope = Scope.EnvironmentWrite)
    public Result create(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");

        EnvironmentObject environmentObject = Json.fromJson(request().body().asJson(), EnvironmentObject.class);
        try {
            environmentObject = environmentService.create(environmentObject.getEnvironment(projectId, userId, 0));
        } catch (NoProjectExistException e) {
            return badRequest();
        }

        return ok(Json.toJson(environmentObject));
    }
}
