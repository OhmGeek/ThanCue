package ThanCue;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import static ThanCue.CueType.*;

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
    private Label lblCueType;
    @FXML
    private Label lblCueName;

    //Selectors
    @FXML
    private ComboBox cmbCueType;

    //Text areas
    @FXML
    private TextField txtCueName;

    //Form buttons
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancelChanges;

    @FXML
    public void initialize() {
        setActions();
        setSizes();

        cmbCueType.getItems().setAll(CueType.values());
        cmbCueType.getSelectionModel().select(UNSET);
    }

    private void setActions() {
        cmbCueType.setOnAction(event -> changeCueType());
        //todo this method
    }

    private void setSizes() {
        //allow growing
        btnSaveChanges.setMaxWidth(Double.MAX_VALUE);
        btnCancelChanges.setMaxWidth(Double.MAX_VALUE);
        lblCueInfo.setMaxWidth(Double.MAX_VALUE);
        lblCueType.setMaxWidth(Double.MAX_VALUE);
        lblCueName.setMaxWidth(Double.MAX_VALUE);

        //ensure grid pane always fill width of parent
        AnchorPane.setTopAnchor(grid_pane_form_edit_cue, .0);
        AnchorPane.setBottomAnchor(grid_pane_form_edit_cue, .0);
        AnchorPane.setLeftAnchor(grid_pane_form_edit_cue, .0);
        AnchorPane.setRightAnchor(grid_pane_form_edit_cue, .0);

        //make grid columns wide as parent
        ColumnConstraints columnConstraintForMaxWidth = new ColumnConstraints();
        columnConstraintForMaxWidth.setHgrow(Priority.SOMETIMES);
        columnConstraintForMaxWidth.setFillWidth(true);
        grid_pane_form_edit_cue.getColumnConstraints().addAll(columnConstraintForMaxWidth, columnConstraintForMaxWidth);

        //make grid pane tall as parent (on a row by row basis)
        /*RowConstraints rowConstraintNoGrow = new RowConstraints();
        RowConstraints rowConstraintMaybeGrow = new RowConstraints();
        RowConstraints rowConstraintGrow = new RowConstraints();
        rowConstraintNoGrow.setVgrow(Priority.NEVER);
        grid_pane_form_edit_cue.getRowConstraints().add(rowConstraintNoGrow);
        grid_pane_form_edit_cue.getRowConstraints().add(rowConstraintNoGrow);
        grid_pane_form_edit_cue.getRowConstraints().add(rowConstraintGrow);
        grid_pane_form_edit_cue.getRowConstraints().add(rowConstraintMaybeGrow);
*/

        //update layouts
        anchor_pane_form_edit_cue.layout();
        grid_pane_form_edit_cue.layout();
        //todo to finish
    }

    public void setEditObject(Cue c) {
        if (c != null) {
            editingCue = c;
        } else {
            editingCue = new SoundCue();
        }
        updateFieldEntries(true);
    }

    private void updateFieldEntries(boolean updateComboBox) {
        // todo set form labels and option boxes etc to match 'editingCue's properties here, not just one master label (which should be removed eventually)
        lblCueInfo.setText(editingCue.toString());
        if (!updateComboBox) {
            cmbCueType.getSelectionModel().select(editingCue.cueTypeNonProperty);
        }
        txtCueName.setText(editingCue.getCueName());
    }

    private void changeCueType() {
        Cue c = new UnknownCue();
        switch ((CueType) cmbCueType.getSelectionModel().getSelectedItem()) {
            case UNSET:
                //todo we don't want to allow creation of unset cues, this is only there to be a default value
                break;
            case UNKNOWN:
                c = new UnknownCue();
                break;
            case SOUND:
                c = new SoundCue();
                break;
            case LIGHT:
                //todo make light cue a thing
                break;
            case VIDEO:
                //todo make video cue a thing
                break;
        }
        c.setCueName(txtCueName.getText());
        // todo c.setCueBehaviour();
        editingCue = c;
        updateFieldEntries(false);
    }
}
