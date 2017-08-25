package repository.mongo;

import com.google.inject.Inject;
import model.Webhook;
import org.bson.types.ObjectId;
import repository.IWebhookRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 16/01/17.
 */
public class WebhookRepository extends BaseMasterRepository implements IWebhookRepository {

    @Inject
    public WebhookRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Webhook create(Webhook webhook) {
        if (webhook.getId() == null) {
            webhook.setId(new ObjectId());
        }
        datastore.save(webhook);
        return webhook;
    }

    @Override
    public List<Webhook> getAll(String projectId) {
        return getObjects(Webhook.class, "projectId", projectId);
    }
}
