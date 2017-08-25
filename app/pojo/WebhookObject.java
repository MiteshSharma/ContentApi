package pojo;

import event_handler.EventName;
import model.Webhook;
import model.WebhookType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 16/01/17.
 */
public class WebhookObject {
    private String id;
    private EventName eventName;
    private Map<String, String> fieldValues;
    private WebhookType webhookType;
    private String webhookData;
    private boolean isSync;

    public WebhookObject() {}

    public WebhookObject(String id, EventName eventName, Map<String, String> fieldValues,
                         WebhookType webhookType, String webhookData, boolean isSync) {
        this.id = id;
        this.eventName = eventName;
        this.fieldValues = fieldValues;
        this.webhookType = webhookType;
        this.webhookData = webhookData;
        this.isSync = isSync;
    }

    public String getId() {
        return id;
    }

    public EventName getEventName() {
        return eventName;
    }

    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    public WebhookType getWebhookType() {
        return webhookType;
    }

    public String getWebhookData() {
        return webhookData;
    }

    public boolean getIsSync() {
        return isSync;
    }

    public Webhook getWebhook(String projectId, String userId) {
        return new Webhook(this.id, projectId, userId, this.eventName, this.fieldValues,
                this.webhookType, this.webhookData, this.isSync);
    }

    public static WebhookObject getWebhookObject(Webhook webhook) {
        return new WebhookObject(webhook.getId().toString(), webhook.getEventName(), webhook.getFieldValues(),
                webhook.getWebhookType(), webhook.getWebhookData(), webhook.getIsSync());
    }

    public static List<WebhookObject> getWebhookObjects(List<Webhook> webhooks) {
        List<WebhookObject> webhookObjects = new ArrayList<>();
        for (Webhook webhook : webhooks) {
            webhookObjects.add(getWebhookObject(webhook));
        }
        return webhookObjects;
    }
}
