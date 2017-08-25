package repository.mongo;

import com.google.inject.Inject;
import model.TeamInvite;
import org.bson.types.ObjectId;
import repository.ITeamInviteRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 05/01/17.
 */
public class TeamInviteRepository extends BaseMasterRepository implements ITeamInviteRepository {
    @Inject
    public TeamInviteRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public TeamInvite create(TeamInvite teamInvite) {
        if (teamInvite.getId() == null) {
            teamInvite.setId(new ObjectId());
        }
        datastore.save(teamInvite);
        return teamInvite;
    }

    @Override
    public List<TeamInvite> get(String email) {
        return getObjects(TeamInvite.class, "email", email);
    }
}
