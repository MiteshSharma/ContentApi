package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import exceptions.NoProjectExistException;
import model.TeamInvite;
import services.impl.TeamInviteService;

import java.util.List;

/**
 * Created by mitesh on 05/01/17.
 */
@ImplementedBy(TeamInviteService.class)
public interface ITeamInviteService {
    public void invite(String projectId, JsonNode jsonNode) throws NoProjectExistException;
    public List<TeamInvite> get(String email);
}
