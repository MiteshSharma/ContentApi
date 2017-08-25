package repository;

import com.google.inject.ImplementedBy;
import model.ProjectApiKey;
import repository.mongo.ProjectApiKeyRepository;

/**
 * Created by mitesh on 22/12/16.
 */
@ImplementedBy(ProjectApiKeyRepository.class)
public interface IProjectApiKeyRepository {
    public ProjectApiKey create(ProjectApiKey projectApiKey);
    public ProjectApiKey get(String apiKey);
    public ProjectApiKey getByProjectId(String projectId, String environmentId);
}
