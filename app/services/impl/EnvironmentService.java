package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import model.Environment;
import model.Project;
import pojo.EnvironmentObject;
import repository.IEnvironmentRepository;
import repository.IProjectRepository;
import services.IEnvironmentService;
import services.IProjectApiKeyService;
import services.IProjectService;

import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 18/11/16.
 */
public class EnvironmentService implements IEnvironmentService {
    @Inject
    IEnvironmentRepository environmentRepository;
    @Inject
    IProjectRepository projectRepository;
    @Inject
    IProjectApiKeyService projectApiKeyService;

    @Override
    public EnvironmentObject create(String projectId, String userId) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }

        Environment environment = new Environment(null, "Production", "Production", "This is production environment.",
                projectId, userId);

        return this.create(environment);
    }

    @Override
    public EnvironmentObject create(Environment environment) throws NoProjectExistException {
        Project project = this.projectRepository.get(environment.getProjectId());
        if (project == null) {
            throw new NoProjectExistException();
        }

        environment = environmentRepository.create(environment);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("environmentName", environment.getName());
        EventDispatcher.dispatch(EventName.ENVIRONMENT_CREATE, eventParam);

        this.projectApiKeyService.create(project, environment);

        return EnvironmentObject.getEnvironmentObject(environment);
    }

    @Override
    public List<EnvironmentObject> getByProject(String projectId) {
        List<Environment> environments = this.environmentRepository.getByProject(projectId);
        return EnvironmentObject.getEnvironmentObjects(environments);
    }
}