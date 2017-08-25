package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import model.Environment;
import model.Project;
import model.ProjectApiKey;
import repository.IProjectApiKeyRepository;
import repository.IProjectRepository;
import services.IProjectApiKeyService;

import java.util.Map;
import java.util.UUID;

/**
 * Created by mitesh on 22/12/16.
 */
public class ProjectApiKeyService implements IProjectApiKeyService {
    @Inject
    IProjectApiKeyRepository projectApiKeyRepository;
    @Inject
    IProjectRepository projectRepository;

    @Override
    public ProjectApiKey create(Project project, Environment environment) {
        ProjectApiKey projectApiKey = new ProjectApiKey(null, project.getId().toString(), environment.getId().toString(), UUID.randomUUID().toString());
        projectApiKey = this.projectApiKeyRepository.create(projectApiKey);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("environmentId", environment.getId().toString());
        EventDispatcher.dispatch(EventName.PROJECT_KEY_CREATE, eventParam);

        return projectApiKey;
    }

    @Override
    public ProjectApiKey get(String apiKey) {
        return this.projectApiKeyRepository.get(apiKey);
    }

    @Override
    public ProjectApiKey getByProjectId(String projectId, String environmentId) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        return this.projectApiKeyRepository.getByProjectId(projectId, environmentId);
    }
}
