package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import model.*;
import pojo.TeamObject;
import repository.IProjectRepository;
import repository.ITeamInviteRepository;
import services.ITeamInviteService;
import services.ITeamService;
import services.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 05/01/17.
 */
public class TeamInviteService implements ITeamInviteService {

    @Inject
    IUserService userService;
    @Inject
    ITeamService teamService;
    @Inject
    ITeamInviteRepository teamInviteRepository;
    @Inject
    IProjectRepository projectRepository;

    @Override
    public void invite(String projectId, JsonNode jsonNode) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        String email = jsonNode.get("email").asText();
        String role = jsonNode.get("role").asText();
        ERole eRole = ERole.valueOf(role);
        User invitedUser = userService.getByEmail(email);
        if (invitedUser != null) {
            TeamObject teamObject = teamService.get(projectId);
            TeamMember teamMember = new TeamMember(invitedUser, ERole.MEMBER, new Date());
            teamService.update(teamObject.getId(), teamMember);
        } else {
            TeamInvite teamInvite = new TeamInvite(null, projectId, email, eRole);
            teamInviteRepository.create(teamInvite);

            Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
            eventParam.put("projectId", project.getId().toString());
            eventParam.put("projectName", project.getName());
            eventParam.put("email", teamInvite.getEmail());
            eventParam.put("role", teamInvite.getRole().toString());
            EventDispatcher.dispatch(EventName.TEAM_INVITE_CREATE, eventParam);
        }
    }

    @Override
    public List<TeamInvite> get(String email) {
        return teamInviteRepository.get(email);
    }
}
