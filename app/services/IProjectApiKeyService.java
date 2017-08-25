package services;

import com.google.inject.ImplementedBy;
import exceptions.NoAppExistException;
import exceptions.NoProjectExistException;
import model.Environment;
import model.Project;
import model.ProjectApiKey;
import services.impl.ProjectApiKeyService;

/**
 * Created by mitesh on 22/12/16.
 */
@ImplementedBy(ProjectApiKeyService.class)
public interface IProjectApiKeyService {
    public ProjectApiKey create(Project project, Environment environment);
    public ProjectApiKey get(String apiKey);
    public ProjectApiKey getByProjectId(String projectId, String environmentId) throws NoProjectExistException;
}
