package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import model.Project;
import org.bson.types.ObjectId;
import repository.IProjectRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class ProjectRepository extends BaseMasterRepository implements IProjectRepository {

    @Inject
    public ProjectRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Project create(Project project) {
        if (project.getId() == null) {
            project.setId(new ObjectId());
        }
        datastore.save(project);
        return project;
    }

    @Override
    public Project update(Project project) {
        BasicDBObject selectObject = new BasicDBObject("_id", project.getId());
        BasicDBObject updateData = new BasicDBObject();
        updateData.put("displayName", project.getDisplayName());
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateData);

        DBCollection projectDb = this.datastore.getCollection(Project.class);
        projectDb.update(selectObject, setQuery);
        return project;
    }

    @Override
    public Project get(String projectId) {
        return getById(Project.class, projectId);
    }

    @Override
    public Project incrementCollectionCount(Project project, int count) {
        BasicDBObject selectObject = new BasicDBObject("_id", project.getId());
        BasicDBObject incValue = new BasicDBObject("totalModel", count);
        BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

        DBCollection projectDb = this.datastore.getCollection(Project.class);
        projectDb.update(selectObject, intModifier);

        project.setTotalContent(project.getTotalContent() + count);
        return project;
    }

    @Override
    public Project incrementTotalContentCount(Project project, int count) {
        BasicDBObject selectObject = new BasicDBObject("_id", project.getId());
        BasicDBObject incValue = new BasicDBObject("totalContent", count);
        BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

        DBCollection projectDb = this.datastore.getCollection(Project.class);
        projectDb.update(selectObject, intModifier);

        return project;
    }

    @Override
    public Project incrementTotalMediaCount(Project project, int count) {
        BasicDBObject selectObject = new BasicDBObject("_id", project.getId());
        BasicDBObject incValue = new BasicDBObject("totalMedia", count);
        BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

        DBCollection projectDb = this.datastore.getCollection(Project.class);
        projectDb.update(selectObject, intModifier);

        return project;
    }

    @Override
    public List<Project> getByApp(String appId) {
        return getObjects(Project.class, "appId", appId);
    }
}
