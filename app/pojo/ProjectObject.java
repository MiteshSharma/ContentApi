package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Project;
import model.ProjectDailyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class ProjectObject {
    private String id;
    private String name;
    private String displayName;
    private long totalModel;
    private long totalContent;
    private long totalMedia;
    private TeamObject teamObject;
    private List<EnvironmentObject> environmentObjects;
    private List<ProjectDailyActivity> dailyActivities;
    private List<Scope> scopes;

    public ProjectObject() {}

    public ProjectObject(String id, String name, String displayName, long totalModel, long totalContent, long totalMedia) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.totalModel = totalModel;
        this.totalContent = totalContent;
        this.totalMedia = totalMedia;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getTotalModel() {
        return totalModel;
    }

    public long getTotalContent() {
        return totalContent;
    }

    public long getTotalMedia() {
        return totalMedia;
    }

    public List<EnvironmentObject> getEnvironmentObjects() {
        return environmentObjects;
    }

    public TeamObject getTeamObject() {
        return teamObject;
    }

    public List<ProjectDailyActivity> getDailyActivities() {
        return dailyActivities;
    }

    public void setDailyActivities(List<ProjectDailyActivity> dailyActivities) {
        this.dailyActivities = dailyActivities;
    }

    public void setTeamObject(TeamObject teamObject) {
        this.teamObject = teamObject;
    }

    public void setEnvironmentObjects(List<EnvironmentObject> environmentObjects) {
        this.environmentObjects = environmentObjects;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    @JsonIgnore
    public Project getProject(String userId) {
        return new Project(this.id, this.name, this.displayName, userId);
    }

    public static ProjectObject getProjectObject(Project project) {
        return new ProjectObject(project.getId().toString(), project.getName(),
                project.getDisplayName(), project.getTotalModel(), project.getTotalContent(), project.getTotalMedia());
    }

    public static List<ProjectObject> getProjectObjects(List<Project> projects) {
        List<ProjectObject> projectObjects = new ArrayList<>();
        for (Project project: projects) {
            projectObjects.add(getProjectObject(project));
        }
        return projectObjects;
    }
}