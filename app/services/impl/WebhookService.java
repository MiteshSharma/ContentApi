package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import model.Webhook;
import pojo.WebhookObject;
import repository.IWebhookRepository;
import services.IWebhookService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 16/01/17.
 */
public class WebhookService implements IWebhookService {

    @Inject
    IWebhookRepository webhookRepository;

    @Override
    public WebhookObject create(WebhookObject webhookObject, String projectId, String userId) {
        Webhook webhook = webhookObject.getWebhook(projectId, userId);
        webhook = webhookRepository.create(webhook);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", projectId);
        eventParam.put("webhookType", webhook.getWebhookType().toString());
        eventParam.put("eventName", webhook.getEventName().toString());
        EventDispatcher.dispatch(EventName.WEBHOOK_CREATE, eventParam);
        return WebhookObject.getWebhookObject(webhook);
    }

    @Override
    public List<WebhookObject> getAll(String projectId) {
        List<Webhook> webhooks = webhookRepository.getAll(projectId);
        return WebhookObject.getWebhookObjects(webhooks);
    }

    @Override
    public List<EventName> getAllEvents() {
        return EventName.getClientEvents();
    }
}
