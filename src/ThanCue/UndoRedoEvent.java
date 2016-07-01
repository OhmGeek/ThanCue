package ThanCue;

/**
 * This is an object that stores information about some undo event (the old value, the new value,
 * in addition to the event (rather, the property that was changed). We can use this tuple to
 * undo/redo in our engine.
 *
 * Created by ryan on 28/06/16.
 */
public class UndoRedoEvent<T> implements UndoRedoEventInterface<T> {
    //todo implement methods
    private T oldValue;
    private T newValue;
    private EventTypeInterface event;

    public UndoRedoEvent(T oldValue, T newValue, EventTypeInterface event) {
        setOldValue(oldValue);
        setNewValue(newValue);
        setEventType(event);
    }


    public T getOldValue() {
        return null;
    }

    public void setOldValue(T oldValue) {

    }

    public T getNewValue() {
        return null;
    }
    public void setNewValue(T newValue) {

    }

    public EventTypeInterface getEventType() {
        return null;
    }
    public void setEventType(EventTypeInterface eventType) {

    }


}
