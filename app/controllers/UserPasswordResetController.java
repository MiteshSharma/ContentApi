package controllers;

import com.google.inject.Inject;
import helpers.ApiAuth;
import model.User;
import play.mvc.Result;
import services.IUserService;

/**
 * Created by mitesh on 26/11/16.
 */
public class UserPasswordResetController extends CoreController {

    @Inject
    IUserService userService;

    @ApiAuth
    public Result create(String email, String projectId) {
        User user = new User();
        user.setEmail(email);
        user.setProjectId(projectId);
        if (userService.isUserExist(user)) {
            return ok();
        }
        return badRequest("No user registered by this email.");
    }

    public Result update() {
        return ok();
    }
}