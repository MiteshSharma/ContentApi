package repository.mongo;

import com.google.inject.Inject;
import model.Team;
import model.TeamMember;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import repository.ITeamRepository;
import repository.mongo.driver.IDbStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
public class TeamRepository extends BaseMasterRepository implements ITeamRepository {
    @Inject
    public TeamRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Team create(Team team) {
        if (team.getId() == null) {
            team.setId(new ObjectId());
        }
        datastore.save(team);
        return team;
    }

    @Override
    public Team update(String teamId, TeamMember teamMember) {
        Team team = get(teamId);
        List<TeamMember> teamMemberList = team.getTeamMembers();
        if (teamMemberList == null) {
            teamMemberList = new ArrayList<>();
        }
        teamMemberList.add(teamMember);
        team.setTeamMembers(teamMemberList);

        datastore.save(team);

        return team;
    }

    @Override
    public Team deleteTeamMember(String teamId, String memberId) {
        Team team = get(teamId);
        List<TeamMember> teamMemberList = team.getTeamMembers();
        TeamMember toDeleteTeamMember = null;
        for (TeamMember teamMember : teamMemberList) {
            if (teamMember.getUserId() == memberId) {
                toDeleteTeamMember = teamMember;
            }
        }
        if (toDeleteTeamMember != null) {
            teamMemberList.remove(toDeleteTeamMember);
        }
        team.setTeamMembers(teamMemberList);
        datastore.save(team);

        return team;
    }

    @Override
    public Team get(String teamId) {
        return getById(Team.class, teamId);
    }

    @Override
    public Team getByProjectId(String projectId) {
        return getObject(Team.class, "projectId", projectId);
    }

    @Override
    public List<Team> getByUserId(String memberId) {
        return getObjects(Team.class, "teamMembers.userId", memberId);
    }
}
