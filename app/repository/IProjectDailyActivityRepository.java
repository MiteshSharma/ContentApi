package repository;

import com.google.inject.ImplementedBy;
import model.ProjectDailyActivity;
import repository.mongo.ProjectDailyActivityRepository;

import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
@ImplementedBy(ProjectDailyActivityRepository.class)
public interface IProjectDailyActivityRepository {
    public ProjectDailyActivity createUpdateDailyActivity(String projectId, int modelCount,
                                                          int contentCount, int mediaCount);
    public List<ProjectDailyActivity> getLastWeekActivity(String projectId);
}
