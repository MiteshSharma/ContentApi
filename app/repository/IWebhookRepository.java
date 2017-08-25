package repository;

import com.google.inject.ImplementedBy;
import model.Webhook;
import repository.mongo.WebhookRepository;

import java.util.List;

/**
 * Created by mitesh on 16/01/17.
 */
@ImplementedBy(WebhookRepository.class)
public interface IWebhookRepository {
    public Webhook create(Webhook webhook);
    public List<Webhook> getAll(String projectId);
}
