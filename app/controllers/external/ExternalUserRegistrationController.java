package controllers.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.CoreController;
import exceptions.UserLoginFailedException;
import helpers.ExternalApiAuth;
import model.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.IUserService;

/**
 * Created by mitesh on 23/12/16.
 */
public class ExternalUserRegistrationController extends CoreController {
    @Inject
    IUserService userService;

    @ExternalApiAuth
    public Result create() {
        String projectId = (String) Http.Context.current().args.get("projectId");
        JsonNode userLoginJson = request().body().asJson();
        if (userLoginJson.get("email") == null || userLoginJson.get("password") == null) {
            return badRequest();
        }
        User user = new User();
        user.setEmail(userLoginJson.get("email").asText());
        user.setPassword(userLoginJson.get("password").asText());
        user.setProjectId(projectId);
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

    @ExternalApiAuth
    public Result delete() {
        userService.logout();
        return ok();
    }
}
