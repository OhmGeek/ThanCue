package ThanCue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Created by mike on 04/06/16.
 */
public class FormEditCueController {
    // todo the rest of this class and stuff

    private Cue editingCue;
    ObservableList<String> cueTypeOptions =
            FXCollections.observableArrayList(
                    "Sound Cue",
                    "Video Cue",
                    "Light Cue",
                    "Unknown Cue" //todo this system would be easier if type were an enum, and we just got the string version like we do for behaviour
            );

    //Container panes
    @FXML
    private AnchorPane anchor_pane_form_edit_cue;
    @FXML
    private GridPane grid_pane_form_edit_cue;

    //Labels
    @FXML
    private Label lblCueInfo;

    //Selectors
    @FXML
    private ComboBox cmbCueType;

    @FXML
    public void initialize() {
        cmbCueType.getItems().addAll(cueTypeOptions);
    }

    public void setEditObject(Cue c){
        if (c != null) {
            editingCue = c;
            editingCue.setCueName("Edited cue - " + editingCue.getCueName()); // todo remove later, is for testing
            updateInfoLabels();
        } else {
            // must wait for type of cue to be set by user // todo this means we do what?
        }
    }

    private void updateInfoLabels() {
        // todo set form labels and option boxes etc to match 'editingCue's properties here, not just one master label (which should be removed eventually)
        lblCueInfo.setText(editingCue.toString());
    }
}
