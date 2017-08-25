package event_handler;

import dispatcher.IEventHandler;
import play.Logger;

import java.util.Map;

/**
 * Created by mitesh on 15/08/16.
 */
public class BaseEventHandler implements IEventHandler {
    @Override
    public void handle(EventName eventName, Map<String, String> params) {
        Logger.info("Event {} fired", eventName);
    }
}