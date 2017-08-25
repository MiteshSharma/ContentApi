package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Team;
import model.TeamMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
public class TeamObject {
    private String id;
    private String projectId;
    private List<TeamMember> teamMembers = new ArrayList<>();

    public TeamObject() {
    }

    public TeamObject(String id, String projectId, List<TeamMember> teamMembers) {
        this.id = id;
        this.projectId = projectId;
        this.teamMembers = teamMembers;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getProjectId() {
        return projectId;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public static TeamObject getTeamObject(Team team) {
        if (team.getId() == null) {
            return  null;
        }
        return new TeamObject(team.getId().toString(), team.getProjectId(), team.getTeamMembers());
    }

    public static List<TeamObject> getTeamObjects(List<Team> teams) {
        List<TeamObject> teamObjects = new ArrayList<>();
        for (Team team : teams) {
            teamObjects.add(getTeamObject(team));
        }
        return teamObjects;
    }
}
