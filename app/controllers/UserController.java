package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import exceptions.UserLoginFailedException;
import helpers.ApiAuth;
import model.TeamInvite;
import model.TeamMember;
import model.User;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.TeamObject;
import services.ITeamInviteService;
import services.ITeamService;
import services.IUserService;

import java.util.Date;
import java.util.List;

/**
 * Created by mitesh on 16/11/16.
 */
public class UserController extends CoreController {

    @Inject
    IUserService userService;
    @Inject
    ITeamInviteService teamInviteService;
    @Inject
    ITeamService teamService;

    public Result create() {
        User user = Json.fromJson(request().body().asJson(), User.class);
        JsonNode userJson = request().body().asJson();
        if (!user.isValid()) {
            return badRequest("Please provide name and email.");
        }
        if (userJson.get("password") == null) {
            return badRequest("Please provide password.");
        }
        user.setPassword(userJson.get("password").asText());
        if (userService.isUserExist(user)) {
            return badRequest("This email is already registered.");
        }
        user.setEmail(user.getEmail().toLowerCase());
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
        jsonNode.put("isExist", false);

        List<TeamInvite> teamInvites = this.teamInviteService.get(user.getEmail());
        if (teamInvites != null && teamInvites.size() > 0) {
            for (TeamInvite teamInvite : teamInvites) {
                TeamObject teamObject = teamService.get(teamInvite.getProjectId());
                TeamMember teamMember = new TeamMember(user, teamInvite.getRole(), new Date());
                this.teamService.update(teamObject.getId(), teamMember);
            }
            jsonNode.put("isExist", true);
        }
        return ok(Json.toJson(jsonNode));
    }

    @ApiAuth
    public Result get() {
        String userId = (String) Http.Context.current().args.get("userId");
        User user = userService.get(userId);
        return ok(Json.toJson(user));
    }

    @ApiAuth
    public Result update() {
        User user = Json.fromJson(request().body().asJson(), User.class);
        if (!user.isValid()) {
            return badRequest("Please provide name and email.");
        }
        user.setEmail(user.getEmail().toLowerCase());
        userService.update(user);
        return ok();
    }
}
