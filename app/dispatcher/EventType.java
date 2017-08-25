package dispatcher;

/**
 * Created by mitesh on 15/08/16.
 */
public enum  EventType implements IEventType {
    MAIN_EVENT;

    @Override
    public IEventType getEventType() {
        return this;
    }
}
