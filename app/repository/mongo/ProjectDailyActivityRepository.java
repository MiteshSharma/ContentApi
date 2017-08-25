package repository.mongo;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import helpers.TimeHelper;
import model.Project;
import model.ProjectDailyActivity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import repository.IProjectDailyActivityRepository;
import repository.mongo.driver.IDbStore;

import java.util.Date;
import java.util.List;

/**
 * Created by mitesh on 20/11/16.
 */
public class ProjectDailyActivityRepository extends BaseMasterRepository implements IProjectDailyActivityRepository {
    @Inject
    public ProjectDailyActivityRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public ProjectDailyActivity createUpdateDailyActivity(String projectId, int modelCount, int contentCount, int mediaCount) {
        ProjectDailyActivity dailyActivity = this.datastore.createQuery(ProjectDailyActivity.class).
                field("projectId").equal(projectId).
                field("date").equal(TimeHelper.getYearMonthDay(new Date())).get();
        if (dailyActivity == null) {
            dailyActivity = new ProjectDailyActivity(new ObjectId(), projectId,
                    TimeHelper.getYearMonthDay(new Date()), modelCount, contentCount, mediaCount);
            this.datastore.save(dailyActivity);
        } else {
            dailyActivity.setTotalModel(dailyActivity.getTotalModel() + modelCount);
            dailyActivity.setTotalContent(dailyActivity.getTotalContent() + contentCount);
            dailyActivity.setTotalMedia(dailyActivity.getTotalMedia() + mediaCount);

            BasicDBObject selectObject = new BasicDBObject("_id", dailyActivity.getId());
            BasicDBObject incValue = new BasicDBObject();
            incValue.put("totalModel", modelCount);
            incValue.put("totalContent", contentCount);
            incValue.put("totalMedia", mediaCount);
            BasicDBObject intModifier = new BasicDBObject("$inc", incValue);

            DBCollection dailyActivityDb = this.datastore.getCollection(ProjectDailyActivity.class);
            dailyActivityDb.update(selectObject, intModifier);
        }
        return dailyActivity;
    }

    @Override
    public List<ProjectDailyActivity> getLastWeekActivity(String projectId) {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));

        Query<ProjectDailyActivity> query = datastore.createQuery(ProjectDailyActivity.class);
        query.filter("date >=", TimeHelper.getYearMonthDay(date));
        query.filter("projectId ==", projectId);
        return query.asList();
    }
}
