package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.NoProjectExistException;
import helpers.ApiAuth;
import play.mvc.Result;
import pojo.Scope;
import services.ITeamInviteService;

/**
 * Created by mitesh on 05/01/17.
 */
public class TeamInviteController extends CoreController {

    @Inject
    ITeamInviteService teamInviteService;

    @ApiAuth(scope = Scope.TeamWrite)
    public Result create(String projectId) {
        JsonNode jsonNode = request().body().asJson();
        JsonNode email = jsonNode.get("email");
        JsonNode role = jsonNode.get("role");

        if (email == null || role == null) {
            return badRequest();
        }

        try {
            teamInviteService.invite(projectId, jsonNode);
        } catch (NoProjectExistException e) {
            return internalServerError();
        }
        return ok();
    }
}
