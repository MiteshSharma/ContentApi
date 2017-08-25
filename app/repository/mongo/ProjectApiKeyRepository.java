package repository.mongo;

import com.google.inject.Inject;
import model.ProjectApiKey;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import repository.IProjectApiKeyRepository;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 22/12/16.
 */
public class ProjectApiKeyRepository extends BaseMasterRepository implements IProjectApiKeyRepository {

    @Inject
    public ProjectApiKeyRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public ProjectApiKey create(ProjectApiKey projectApiKey) {
        if (projectApiKey.getId() == null) {
            projectApiKey.setId(new ObjectId());
        }
        datastore.save(projectApiKey);
        return projectApiKey;
    }

    @Override
    public ProjectApiKey get(String apiKey) {
        return getObject(ProjectApiKey.class, "apiKey", apiKey);
    }

    @Override
    public ProjectApiKey getByProjectId(String projectId, String environmentId) {
        Query<ProjectApiKey> response =  this.datastore.createQuery(ProjectApiKey.class).field("projectId")
                .equal(projectId).field("environmentId").equal(environmentId);
        return response.get();
    }
}
