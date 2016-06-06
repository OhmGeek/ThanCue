package ThanCue;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Created by mike on 04/06/16.
 */
public class FormEditCueController {
    // todo the rest of this class and stuff

    private Cue editingCue;

    //Container panes
    @FXML
    private AnchorPane anchor_pane_form_edit_cue;
    @FXML
    private GridPane grid_pane_form_edit_cue;

    //Labels
    @FXML
    private Label lblCueInfo;

    @FXML
    public void initialize() {

    }

    public void setEditObject(Cue c){
        if (c != null) {
            editingCue = c;
            updateInfoLabels();
        } else {
            // must wait for type of cue to be set by user // todo this means we do what?
        }
    }

    private void updateInfoLabels() {
        // todo set form labels to match 'editingCue's properties here, not just one master label (which should be removed eventually)
        lblCueInfo.setText(editingCue.toString());
    }
}
