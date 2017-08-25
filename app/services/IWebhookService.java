package services;

import com.google.inject.ImplementedBy;
import event_handler.EventName;
import pojo.WebhookObject;
import services.impl.WebhookService;

import java.util.List;

/**
 * Created by mitesh on 16/01/17.
 */
@ImplementedBy(WebhookService.class)
public interface IWebhookService {
    public WebhookObject create(WebhookObject webhookObject, String projectId, String userId);
    public List<WebhookObject> getAll(String projectId);
    public List<EventName> getAllEvents();
}
