package repository;

import com.google.inject.ImplementedBy;
import model.Environment;
import repository.mongo.EnvironmentRepository;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(EnvironmentRepository.class)
public interface IEnvironmentRepository {
    public Environment create(Environment app);
    public void update(Environment environment);
    public Environment get(String environmentId);
    public Environment incrementTotalContentCount(Environment environment, int count);
    public List<Environment> getByProject(String projectId);
}
