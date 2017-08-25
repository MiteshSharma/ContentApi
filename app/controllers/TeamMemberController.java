package controllers;

import com.google.inject.Inject;
import helpers.ApiAuth;
import model.TeamMember;
import play.libs.Json;
import play.mvc.Result;
import pojo.Scope;
import pojo.TeamObject;
import services.ITeamService;

/**
 * Created by mitesh on 20/11/16.
 */
public class TeamMemberController extends CoreController {
    @Inject
    ITeamService teamService;

    @ApiAuth(scope = Scope.TeamWrite)
    public Result create(String teamId) {
        TeamMember teamMember = Json.fromJson(request().body().asJson(), TeamMember.class);
        TeamObject teamObject = teamService.update(teamId, teamMember);
        return ok(Json.toJson(teamObject));
    }

    @ApiAuth(scope = Scope.TeamDelete)
    public Result delete(String teamId, String memberId) {
        TeamObject teamObject = teamService.delete(teamId, memberId);
        return ok(Json.toJson(teamObject));
    }
}
