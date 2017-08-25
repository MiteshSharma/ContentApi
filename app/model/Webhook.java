package model;

import event_handler.EventName;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Property;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

/**
 * Created by mitesh on 16/01/17.
 */
@Entity(name = "webhooks")
public class Webhook {
    @Id
    private ObjectId _id;
    @Property("projectId")
    private String projectId;
    @Property("userId")
    private String userId;
    @Property("eventName")
    private EventName eventName;
    @Property("fieldValues")
    private Map<String, String> fieldValues;
    @Property("webhookType")
    private WebhookType webhookType;
    @Property("webhookData")
    private String webhookData;
    @Property("isSync")
    private boolean isSync = false;

    public Webhook() {}

    public Webhook(String id, String projectId, String userId, EventName eventName, Map<String, String> fieldValues, WebhookType webhookType, String webhookData, boolean isSync) {
        if (id != null) {
            this._id = new ObjectId(id);
        }
        this.projectId = projectId;
        this.userId = userId;
        this.eventName = eventName;
        this.fieldValues = fieldValues;
        this.webhookType = webhookType;
        this.webhookData = webhookData;
        this.isSync = isSync;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getUserId() {
        return userId;
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
}
