package pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.App;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class AppObject {
    private String id;
    private String name;
    private String userId;
    private List<ProjectObject> projectObjects;

    public AppObject() {
    }

    public AppObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProjectObject> getProjectObjects() {
        return projectObjects;
    }

    public void setProjectObjects(List<ProjectObject> projectObjects) {
        this.projectObjects = projectObjects;
    }

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    @JsonIgnore
    public App getApp(String userId) {
        if (this.id != null) {
            return new App(new ObjectId(this.id), this.name, userId);
        }
        return new App(null, this.name, userId);
    }

    public static AppObject getAppObject(App app) {
        return new AppObject(app.getId().toString(), app.getName());
    }

    public static List<AppObject> getAppObjects(List<App> apps) {
        List<AppObject> appObjects = new ArrayList<>();
        for (App app: apps) {
            appObjects.add(getAppObject(app));
        }
        return appObjects;
    }
}
