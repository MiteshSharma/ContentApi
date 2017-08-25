package repository;

import com.google.inject.ImplementedBy;
import model.TeamInvite;
import repository.mongo.TeamInviteRepository;

import java.util.List;

/**
 * Created by mitesh on 05/01/17.
 */
@ImplementedBy(TeamInviteRepository.class)
public interface ITeamInviteRepository {
    public TeamInvite create(TeamInvite teamInvite);
    public List<TeamInvite> get(String email);
}