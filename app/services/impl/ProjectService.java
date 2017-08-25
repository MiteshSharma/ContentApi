package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import model.*;
import play.Logger;
import pojo.EnvironmentObject;
import pojo.ProjectObject;
import pojo.TeamObject;
import repository.IProjectDailyActivityRepository;
import repository.IProjectRepository;
import services.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 13/11/16.
 */
public class ProjectService implements IProjectService {
    @Inject
    IProjectRepository projectRepository;
    @Inject
    IEnvironmentService environmentService;
    @Inject
    ITeamService teamService;
    @Inject
    IUserService userService;
    @Inject
    IProjectDailyActivityRepository dailyActivityRepository;
    @Inject
    IProjectApiKeyService projectApiKeyService;

    @Override
    public ProjectObject create(Project project) {
        project = this.projectRepository.create(project);
        // Sending BI event for projectCreating
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("userId", project.getUserId());
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        EventDispatcher.dispatch(EventName.PROJECT_CREATE, eventParam);

        Logger.info("Project created with project id as {}", project.getId().toString());

        ProjectObject projectObject = ProjectObject.getProjectObject(project);
        // Create team and add members
        TeamObject teamObject = this.teamService.create(project.getId().toString());
        Logger.info("Team created for project id as {} with team id {}", project.getId().toString(), teamObject.getId());
        User user = userService.get(project.getUserId());
        TeamMember teamMember = new TeamMember(user, ERole.ADMIN, new Date());
        teamObject = this.teamService.update(teamObject.getId(), teamMember);
        Logger.info("Team member with id {} added to team {}", user.getId().toString(), teamObject.getId());

        projectObject.setTeamObject(teamObject);
        // Create environment and add it to project
        EnvironmentObject environmentObject = null;
        try {
            environmentObject = this.environmentService.create(project.getId().toString(), project.getUserId());
        } catch (NoProjectExistException e) {
            // Ignoring as we created project here only
        }
        Logger.info("Environment with id {} created in project id {}", environmentObject.getId(), project.getId());
        List<EnvironmentObject> environmentObjects = new ArrayList<>();
        environmentObjects.add(environmentObject);
        projectObject.setEnvironmentObjects(environmentObjects);
        Logger.info("Project external key is added for project id {}", project.getId());
        this.projectApiKeyService.create(project, environmentObject.getEnvironment(project.getId().toString(), project.getUserId(), 0));
        return projectObject;
    }

    @Override
    public ProjectObject update(Project project) {
        project = this.projectRepository.update(project);
        Logger.info("Project updated with project id as {}", project.getId().toString());
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        EventDispatcher.dispatch(EventName.PROJECT_UPDATE, eventParam);
        return ProjectObject.getProjectObject(project);
    }

    @Override
    public ProjectObject get(String projectId, String userId) {
        Project project = this.projectRepository.get(projectId);
        ProjectObject projectObject = ProjectObject.getProjectObject(project);
        List<EnvironmentObject> environments = this.environmentService.getByProject(project.getId().toString());
        projectObject.setEnvironmentObjects(environments);
        TeamObject teamObject = this.teamService.get(projectId);
        projectObject.setTeamObject(teamObject);

        // Sending scope of this project
        TeamMember teamMember = teamObject.getTeamMembers().stream()
                .filter(tMember -> tMember.getUserId().equals(userId)).findFirst().get();
        projectObject.setScopes(teamMember.getRole().getScopes());

        List<ProjectDailyActivity> dailyActivities = dailyActivityRepository.getLastWeekActivity(projectId);
        projectObject.setDailyActivities(dailyActivities);
        return projectObject;
    }

    @Override
    public List<ProjectObject> getByUser(String userId) {
        List<Project> projects = new ArrayList<>();
        List<TeamObject> teamObjects = this.teamService.getByUser(userId);
        for (TeamObject teamObject : teamObjects) {
            projects.add(this.projectRepository.get(teamObject.getProjectId()));
        }
        return ProjectObject.getProjectObjects(projects);
    }
}
