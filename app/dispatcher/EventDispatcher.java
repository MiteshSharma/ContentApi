package dispatcher;

import com.google.inject.Inject;
import event_handler.EventName;
import play.Configuration;
import play.Play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 15/08/16.
 */
public class EventDispatcher implements IEventDispatcher {

    private static final String EVENT_HANDLER_KEY = "event.handler";
    private static final String DEFAULT_HANDLER_CLASS_NAME = "dispatcher.DefaultEventList";
    private Map<IEventType, List<IEventHandler>> eventHandlers;

    @Inject
    Configuration configuration;

    public EventDispatcher() {}

    public static void dispatch(EventName eventName, Map<String, String> params, IEventType eventType) {
        IEventDispatcher eventDispatcher = Play.application().injector().instanceOf(IEventDispatcher.class);
        eventDispatcher.dispatchEvent(eventName, params, eventType);
    }

    public static void dispatch(EventName eventName, Map<String, String> params) {
        IEventDispatcher eventDispatcher = Play.application().injector().instanceOf(IEventDispatcher.class);
        eventDispatcher.dispatchEvent(eventName, params, EventType.MAIN_EVENT);
    }

    private List<IEventHandler> getHandlers(IEventType eventType) {
        if (eventHandlers == null || !eventHandlers.containsKey(eventType)) {
            eventHandlers = new HashMap<>();
            eventHandlers.put(eventType, new ArrayList<>());
            String eventHandlerClassName = configuration.
                    getString(EVENT_HANDLER_KEY, DEFAULT_HANDLER_CLASS_NAME);
            IEventList eventList = getEventHandlerObject(eventHandlerClassName);
            if (eventList != null) {
                eventHandlers.put(eventType, eventList.eventHandlers(eventType));
            }
        }
        return eventHandlers.get(eventType);
    }

    private IEventList getEventHandlerObject(String className) {
        try {
            Class c = Class.forName(className);
            return (IEventList)c.newInstance();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void dispatchEvent(EventName eventName, Map<String, String> params, IEventType eventType) {
        this.getHandlers(eventType).stream().forEach(eventHandler -> {
            eventHandler.handle(eventName, params);
        });
    }
}
