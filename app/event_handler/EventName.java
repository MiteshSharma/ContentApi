package event_handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 25/07/16.
 */
public enum EventName implements Serializable {
    USER_CREATE(true, "User Create"),
    USER_UPDATE(true, "User Update"),
    USER_LOGIN(true, "User Login"),
    USER_LOGOUT(true, "User Logout"),
    USER_PASSWORD_RESET(true, "User Password Reset"),
    APP_CREATE(false, "App Create"),
    PROJECT_CREATE(false, "Project Create"),
    PROJECT_UPDATE(false, "Project Update"),
    PROJECT_KEY_CREATE(false, "Project Key Create"),
    COLLECTION_CREATE(false, "Collection Create"),
    ENVIRONMENT_CREATE(false, "Environment Create"),
    FIELD_CREATE(false, "Field Create"),
    FIELD_UPDATE(false, "Field Update"),
    FIELD_DELETE(false, "Field Delete"),
    CONTENT_CREATE(true, "Content Create"),
    CONTENT_UPDATE(true, "Content Update"),
    MEDIA_CREATE(true, "Media Create"),
    MEDIA_DELETE(true, "Media Delete"),
    TEAM_CREATE(false, "Team Create"),
    TEAM_MEMBER_CREATE(false, "Team Member Create"),
    TEAM_MEMBER_DELETE(false, "Team Member Delete"),
    TEAM_INVITE_CREATE(false, "Team Invite Create"),
    WEBHOOK_CREATE(false, "Webhook Create"),
    REQUEST_METRICS(false, "Request Metrics");

    private String displayName;
    private boolean isVisible;

    EventName(boolean isVisible, String name) {
        this.isVisible = isVisible;
        this.displayName = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<EventName> getClientEvents() {
        List<EventName> eventNames = new ArrayList<>();
        for (EventName eventName : EventName.values()) {
            if (eventName.isVisible) {
                eventNames.add(eventName);
            }
        }
        return eventNames;
    }
}