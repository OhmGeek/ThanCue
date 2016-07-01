package ThanCue;

/**
 * Created by ryan on 28/06/16.
 */
public interface Undoable {

    //todo update this with the way we do events
    void undo(UndoRedoEvent e); //redoes the event
    void redo(UndoRedoEvent e); //redoes the event
}
