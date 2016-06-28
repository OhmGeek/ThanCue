package ThanCue;

import java.awt.*;

/**
 * Created by ryan on 28/06/16.
 */
public class UndoRedoEngine {
    //todo replace Object with an Event (once I've actually decided how to structure that)
    private CircularStack<Object> undoStack;
    private CircularStack<Object> redoStack;

    public UndoRedoEngine() {
        undoStack = new CircularStack<>(Constants.NUMBER_OF_UNDOS);
        redoStack = new CircularStack<>(Constants.NUMBER_OF_REDOS);
    }
    public void undo() {
        if(!undoStack.empty()) {
            Object eToUndo = undoStack.pop();
        }
    }
    public void redo() {
        if(!undoStack.empty()) {
            Object eToRedo = redoStack.pop();
        }
    }
}
