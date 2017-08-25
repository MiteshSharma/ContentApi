package repository;

import com.google.inject.ImplementedBy;
import model.Team;
import model.TeamMember;
import repository.mongo.TeamRepository;

import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
@ImplementedBy(TeamRepository.class)
public interface ITeamRepository {
    public Team create(Team team);
    public Team update(String teamId, TeamMember teamMember);
    public Team deleteTeamMember(String teamId, String memberId);
    public Team get(String teamId);
    public Team getByProjectId(String projectId);
    public List<Team> getByUserId(String memberId);
}
