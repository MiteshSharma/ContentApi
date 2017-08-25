package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.UserLoginFailedException;
import helpers.ApiAuth;
import model.Project;
import model.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.ProjectObject;
import pojo.Scope;
import services.IProjectService;
import services.IUserService;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class ProjectController extends CoreController {

    @Inject
    IProjectService projectService;
    @Inject
    IUserService userService;

    @ApiAuth(scope = Scope.ProjectWrite)
    public Result create() {
        String userId = (String) Http.Context.current().args.get("userId");

        User user = userService.get(userId);
        if (user == null) {
            return badRequest();
        }

        ProjectObject projectObject = Json.fromJson(request().body().asJson(), ProjectObject.class);
        projectObject = projectService.create(projectObject.getProject(userId));

        String updatedJwt;
        try {
            updatedJwt = userService.login(user);
        } catch (UserLoginFailedException e) {
            return badRequest();
        }

        ObjectNode jsonNode = Json.newObject();
        jsonNode.put("token", updatedJwt);
        jsonNode.putPOJO("projectObject", projectObject);

        return ok(jsonNode);
    }

    @ApiAuth(scope = Scope.ProjectWrite)
    public Result update(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");

        ProjectObject projectObject = Json.fromJson(request().body().asJson(), ProjectObject.class);
        projectObject = projectService.update(projectObject.getProject(userId));
        return ok(Json.toJson(projectObject));
    }

    @ApiAuth
    public Result getAll() {
        String userId = (String) Http.Context.current().args.get("userId");
        List<ProjectObject> projectObjects = projectService.getByUser(userId);
        return ok(Json.toJson(projectObjects));
    }

    @ApiAuth
    public Result get(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");
        ProjectObject projectObject = projectService.get(projectId, userId);
        return ok(Json.toJson(projectObject));
    }
}
