package services;

import com.google.inject.ImplementedBy;
import model.NewsletterSubscriber;
import services.impl.NewsletterService;

/**
 * Created by mitesh on 30/01/17.
 */
@ImplementedBy(NewsletterService.class)
public interface INewsletterService {
    public NewsletterSubscriber create(NewsletterSubscriber subscriber);
}
