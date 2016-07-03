package ThanCue.UndoRedo;

import ThanCue.Variables.Constants;

/**
 * Created by ryan on 28/06/16.
 */
public class UndoRedoEngine {
    private CircularStack<UndoRedoEvent> undoStack;
    private CircularStack<UndoRedoEvent> redoStack;

    public UndoRedoEngine() {
        undoStack = new CircularStack<>(Constants.NUMBER_OF_UNDOS);
        redoStack = new CircularStack<>(Constants.NUMBER_OF_REDOS);
    }
    public void undo() {
        if(!undoStack.empty()) {
            UndoRedoEvent eToUndo = undoStack.pop();
            //here we might want to just do eToUndo.undo()
            //we can let the lower levels do all the hard work :P
        }
    }
    public void redo() {
        if(!undoStack.empty()) {
            UndoRedoEvent eToRedo = redoStack.pop();
        }
        //much the same as undo with eToRedo.redo()
        //nicer this way :P
    }
}
