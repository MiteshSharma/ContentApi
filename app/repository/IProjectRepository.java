package repository;

import com.google.inject.ImplementedBy;
import model.Project;
import repository.mongo.ProjectRepository;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(ProjectRepository.class)
public interface IProjectRepository {
    public Project create(Project project);
    public Project update(Project project);
    public Project get(String projectId);
    public Project incrementCollectionCount(Project project, int count);
    public Project incrementTotalContentCount(Project project, int count);
    public Project incrementTotalMediaCount(Project project, int count);
    public List<Project> getByApp(String appId);
}
