package controllers;

import com.google.inject.Inject;
import exceptions.NoAppExistException;
import exceptions.NoProjectExistException;
import model.ProjectApiKey;
import play.libs.Json;
import play.mvc.Result;
import services.IProjectApiKeyService;

/**
 * Created by mitesh on 22/12/16.
 */
public class ProjectApiKeyController extends CoreController {
    @Inject
    IProjectApiKeyService projectApiKeyService;

    public Result get(String projectId, String envId) {
        ProjectApiKey projectApiKey = null;
        try {
            projectApiKey = this.projectApiKeyService.getByProjectId(projectId, envId);
        } catch (NoProjectExistException e) {
            return badRequest();
        }
        return ok(Json.toJson(projectApiKey));
    }
}
