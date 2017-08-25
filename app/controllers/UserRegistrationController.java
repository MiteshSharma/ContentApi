package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import exceptions.UserLoginFailedException;
import model.User;
import play.libs.Json;
import play.mvc.Result;
import services.IUserService;

/**
 * Created by mitesh on 16/11/16.
 */
public class UserRegistrationController extends CoreController {

    @Inject
    IUserService userService;

    public Result create() {
        JsonNode userLoginJson = request().body().asJson();
        if (userLoginJson.get("email") == null || userLoginJson.get("password") == null) {
            return badRequest();
        }
        User user = new User();
        user.setEmail(userLoginJson.get("email").asText().toLowerCase());
        user.setPassword(userLoginJson.get("password").asText());
        user.setProjectId(userLoginJson.get("projectId").asText());
        user.setType(userLoginJson.get("type").asText());
        if (userLoginJson.get("externalId") != null) {
            user.setExternalId(userLoginJson.get("externalId").asText());
        }
        user = userService.isValidLogin(user);

        if (user == null) {
            return forbidden();
        }

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

    public Result delete() {
        userService.logout();
        return ok();
    }
}
