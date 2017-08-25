package dispatcher;

import java.util.List;

/**
 * Created by mitesh on 15/08/16.
 */
public interface IEventList {
    public List<IEventHandler> eventHandlers(IEventType eventType);
}
