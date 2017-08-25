package dispatcher;

import event_handler.EventName;

import java.util.Map;

/**
 * Created by mitesh on 15/08/16.
 */
public interface IEventHandler {
    public void handle(EventName eventName, Map<String, String> params);
}
