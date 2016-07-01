package ThanCue;


/**
 * This is the interface implemented by all UndoRedo event handlers.
 * We store the old and new values of some event, along with the type of event (as an enum)
 * Created by ryan on 01/07/16.
 */
public interface UndoRedoEventInterface<T> {

    T getOldValue();
    void setOldValue(T oldValue);

    T getNewValue();
    void setNewValue(T newValue);

    EventTypeInterface getEventType();
    void setEventType(EventTypeInterface eventType);
}
