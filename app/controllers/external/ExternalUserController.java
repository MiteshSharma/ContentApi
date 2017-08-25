package controllers.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.UserLoginFailedException;
import helpers.ExternalApiAuth;
import model.User;
import play.Configuration;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.IUserService;

import java.util.Map;

/**
 * Created by mitesh on 23/12/16.
 */
public class ExternalUserController extends CoreController {

    @Inject
    IUserService userService;
    @Inject
    Configuration configuration;

    @ExternalApiAuth
    public Result create() {
        String projectId = (String) Http.Context.current().args.get("projectId");
        User user = Json.fromJson(request().body().asJson(), User.class);
        JsonNode userJson = request().body().asJson();
        if (!user.isValid()) {
            return badRequest("Please provide name, type and email.");
        }
        String type = userJson.get("type").asText();
        if ("Normal".equals(type) && userJson.get("password") == null) {
            return badRequest("Please provide password.");
        }
        user.setProjectId(projectId);
        user.setPassword(userJson.get("password").asText());
        if (userService.isUserExist(user)) {
            return badRequest("This email is already registered.");
        }
        user = userService.create(user);

        String jwt;
        try {
            jwt = userService.login(user);
        } catch (UserLoginFailedException e) {
            e.printStackTrace();
            return internalServerError();
        }
        ObjectNode jsonNode = Json.newObject();
        jsonNode.put("token", jwt);
        return ok(Json.toJson(jsonNode));
    }

    @ExternalApiAuth
    public Result get() {
        String userId = (String) Http.Context.current().args.get("userId");
        User user = userService.get(userId);
        return ok(Json.toJson(user));
    }

    @ExternalApiAuth
    public Result update() {
        String projectId = (String) Http.Context.current().args.get("projectId");
        User user = Json.fromJson(request().body().asJson(), User.class);
        if (!user.isValid()) {
            return badRequest("Please provide name and email.");
        }
        user.setProjectId(projectId);
        userService.update(user);
        return ok(Json.toJson(user));
    }
}
