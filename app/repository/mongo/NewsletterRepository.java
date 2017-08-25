package repository.mongo;

import com.google.inject.Inject;
import model.NewsletterSubscriber;
import org.bson.types.ObjectId;
import repository.INewsletterRepository;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 30/01/17.
 */
public class NewsletterRepository extends BaseMasterRepository implements INewsletterRepository {
    @Inject
    public NewsletterRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public NewsletterSubscriber create(NewsletterSubscriber subscriber) {
        if (subscriber.getId() == null) {
            subscriber.setId(new ObjectId());
        }
        datastore.save(subscriber);
        return subscriber;
    }

    @Override
    public NewsletterSubscriber get(String email) {
        return getObject(NewsletterSubscriber.class, "email", email);
    }
}
