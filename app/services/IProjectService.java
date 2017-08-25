package services;

import com.google.inject.ImplementedBy;
import model.Project;
import pojo.ProjectObject;
import services.impl.ProjectService;

import java.util.List;

/**
 * Created by mitesh on 13/11/16.
 */
@ImplementedBy(ProjectService.class)
public interface IProjectService {
    public ProjectObject create(Project project);
    public ProjectObject update(Project project);
    public ProjectObject get(String projectId, String userId);
    public List<ProjectObject> getByUser(String userId);
}
