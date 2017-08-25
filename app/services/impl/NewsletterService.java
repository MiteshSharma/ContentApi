package services.impl;

import com.google.inject.Inject;
import model.NewsletterSubscriber;
import repository.INewsletterRepository;
import services.INewsletterService;

/**
 * Created by mitesh on 30/01/17.
 */
public class NewsletterService implements INewsletterService {
    @Inject
    INewsletterRepository newsletterRepository;
    @Override
    public NewsletterSubscriber create(NewsletterSubscriber subscriber) {
        return this.newsletterRepository.create(subscriber);
    }
}
