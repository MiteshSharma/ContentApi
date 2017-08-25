package dispatcher;

import event_handler.BaseEventHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mitesh on 15/08/16.
 */
public class DefaultEventList implements IEventList {
    BaseEventHandler eventHandler = new BaseEventHandler();
    @Override
    public List<IEventHandler> eventHandlers(IEventType eventType) {
        return Arrays.asList(eventHandler);
    }
}
