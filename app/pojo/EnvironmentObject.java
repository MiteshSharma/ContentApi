package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 18/11/16.
 */
public class EnvironmentObject {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private long totalContent;

    public EnvironmentObject() {}

    public EnvironmentObject(String id, String name, String displayName, long totalContent) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.totalContent = totalContent;
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

    public String getDescription() {
        return description;
    }

    public long getTotalContent() {
        return totalContent;
    }

    @JsonIgnore
    public Environment getEnvironment(String projectId, String userId, long totalContent) {
        return new Environment(this.id, this.name, this.displayName, this.description, projectId, userId);
    }

    public static EnvironmentObject getEnvironmentObject(Environment environment) {
        return new EnvironmentObject(environment.getId().toString(), environment.getName(), environment.getDisplayName(), environment.getTotalContent());
    }

    public static List<EnvironmentObject> getEnvironmentObjects(List<Environment> environments) {
        List<EnvironmentObject> environmentObjects = new ArrayList<>();
        for (Environment environment : environments) {
            environmentObjects.add(getEnvironmentObject(environment));
        }
        return environmentObjects;
    }
}
