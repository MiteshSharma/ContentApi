package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import model.Team;
import model.TeamMember;
import pojo.TeamObject;
import repository.ITeamRepository;
import services.ITeamService;

import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 20/11/16.
 */
public class TeamService implements ITeamService {

    @Inject
    ITeamRepository teamRepository;

    @Override
    public TeamObject create(String projectId) {
        Team team = new Team(projectId);
        team = teamRepository.create(team);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", team.getProjectId());
        eventParam.put("memberCount", team.getTeamMembers().size()+"");
        EventDispatcher.dispatch(EventName.TEAM_CREATE, eventParam);
        return TeamObject.getTeamObject(team);
    }

    @Override
    public TeamObject update(String teamId, TeamMember teamMember) {
        Team team = teamRepository.update(teamId, teamMember);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", team.getProjectId());
        eventParam.put("memberName", teamMember.getName());
        eventParam.put("memberCount", team.getTeamMembers().size()+"");
        EventDispatcher.dispatch(EventName.TEAM_MEMBER_CREATE, eventParam);
        return TeamObject.getTeamObject(team);
    }

    @Override
    public TeamObject get(String projectId) {
        Team team = teamRepository.getByProjectId(projectId);
        if (team == null) {
            return null;
        }
        return TeamObject.getTeamObject(team);
    }

    @Override
    public List<TeamObject> getByUser(String userId) {
        List<Team> teams = teamRepository.getByUserId(userId);
        return TeamObject.getTeamObjects(teams);
    }

    @Override
    public TeamObject delete(String teamId, String memberId) {
        Team team = teamRepository.deleteTeamMember(teamId, memberId);
        EventDispatcher.dispatch(EventName.TEAM_MEMBER_DELETE, CoreController.getBasicAnalyticsParam());
        return TeamObject.getTeamObject(team);
    }
}
