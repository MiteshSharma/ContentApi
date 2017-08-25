package dispatcher;

import com.google.inject.ImplementedBy;
import event_handler.EventName;

import java.util.Map;

/**
 * Created by mitesh on 15/08/16.
 */
@ImplementedBy(EventDispatcher.class)
public interface IEventDispatcher {
    public void dispatchEvent(EventName eventName, Map<String, String> params, IEventType eventType);
}
