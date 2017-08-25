package repository;

import com.google.inject.ImplementedBy;
import model.NewsletterSubscriber;
import repository.mongo.NewsletterRepository;

/**
 * Created by mitesh on 30/01/17.
 */
@ImplementedBy(NewsletterRepository.class)
public interface INewsletterRepository {
    public NewsletterSubscriber create(NewsletterSubscriber subscriber);
    public NewsletterSubscriber get(String email);
}
