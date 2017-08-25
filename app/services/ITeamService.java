package services;

import com.google.inject.ImplementedBy;
import model.TeamMember;
import pojo.TeamObject;
import services.impl.TeamService;

import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
@ImplementedBy(TeamService.class)
public interface ITeamService {
    public TeamObject create(String projectId);
    public TeamObject update(String teamId, TeamMember teamMember);
    public TeamObject get(String projectId);
    public List<TeamObject> getByUser(String userId);
    public TeamObject delete(String teamId, String memberId);
}
