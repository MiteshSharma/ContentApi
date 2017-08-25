package event_handler;

import dispatcher.IEventHandler;
import dispatcher.IEventList;
import dispatcher.IEventType;
import webhook.WebhookEventHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mitesh on 22/01/17.
 */
public class ApplicationEventList implements IEventList {

    BaseEventHandler eventHandler = new BaseEventHandler();
    WebhookEventHandler webhookEventHandler = new WebhookEventHandler();

    @Override
    public List<IEventHandler> eventHandlers(IEventType eventType) {
        return Arrays.asList(eventHandler, webhookEventHandler);
    }
}
