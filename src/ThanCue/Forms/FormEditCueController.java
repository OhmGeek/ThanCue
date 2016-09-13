package ThanCue.Forms;

import ThanCue.Cues.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by mike on 04/06/16.
 */
public class FormEditCueController {
    // todo the rest of this class and stuff

    private FormMainController parentController;
    private Cue editingCue;
    private boolean cueIsToAdd;

    //Container panes
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private GridPane grid_pane;
    @FXML
    private HBox container_file_chooser;

    //Labels
    @FXML
    private Label lblCueInfo;
    @FXML
    private Label lblCueName;
    @FXML
    private Label lblCueType;
    @FXML
    private Label lblCueBehaviour;
    @FXML
    private Label lblFilePath;
    @FXML
    private Label lblMS, lblMS2, lblMS3;
    @FXML
    private Label lblHotKey;

    //Selectors
    @FXML
    private ComboBox cmbCueType;
    @FXML
    private ComboBox cmbCueBehaviour;
    @FXML
    private ComboBox cmbHotKey;

    //Text areas
    @FXML
    private TextField txtCueName;
    @FXML
    private TextField nmrCuePlayDelay;
    @FXML
    private TextField nmrCueStartPoint;
    @FXML
    private TextField nmrCueDuration;

    //Form buttons
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancelChanges;
    @FXML
    private Button btnChooseFile;

    @FXML
    public void initialize() {
        setActions();
        setSizes();

        cmbCueType.getItems().setAll(CueType.values());
        cmbCueType.getSelectionModel().select(CueType.UNKNOWN);
        cmbCueBehaviour.getItems().setAll(CueBehaviour.values());
        cmbCueBehaviour.getSelectionModel().select(CueBehaviour.PLAY_ON_GO);
        //todo change to combination
        //todo use a better enum
        cmbHotKey.getItems().setAll(KeyCode.values());
        cmbHotKey.getSelectionModel().select(KeyCode.NONCONVERT);
    }

    private void setActions() {
        //buttons
        btnCancelChanges.setOnAction(event -> closeEditCueWindow());
        btnSaveChanges.setOnAction(event -> closeAfterReturningCue());
        btnChooseFile.setOnAction(event -> chooseFile());

        //fields
        cmbCueType.setOnAction(event -> changeCueType());
        txtCueName.textProperty().addListener((observableValue, oldValue, newValue) -> changeCueName()); //text changed
        cmbCueBehaviour.setOnAction(event -> changeCueBehaviour());
        nmrCuePlayDelay.textProperty().addListener((observableValue, old, newValue) -> changeCuePlayDelay(newValue));
        nmrCueStartPoint.textProperty().addListener((observableValue, old, newValue) -> changeCueStartPoint(newValue));
        nmrCueDuration.textProperty().addListener((observableValue, old, newValue) -> changeCueDuration(newValue));
        cmbHotKey.setOnAction(event -> changeCueHotKey());
    }

    private void changeCueHotKey() {
      editingCue.setHotKey((KeyCode) cmbHotKey.getSelectionModel().getSelectedItem());

    }

    private void changeCueDuration(String newValue) {
        newValue = newValue.replaceAll("[^\\d]", "");
        if (newValue.isEmpty()) {
            nmrCueDuration.setText("");
            return;
        }
        nmrCueDuration.setText(newValue);

        long duration = Long.parseLong(newValue);
        if (duration > 0 && duration <= Integer.MAX_VALUE) {
            if (editingCue != null) {
                editingCue.setCueDuration((int) duration);
            }
        } else {
            nmrCueDuration.setText("0");
        }
    }

    private void changeCueStartPoint(String newValue) {
        newValue = newValue.replaceAll("[^\\d]", "");
        if (newValue.isEmpty()) {
            nmrCueStartPoint.setText("");
            return;
        }
        nmrCueStartPoint.setText(newValue);

        long startpoint = Long.parseLong(newValue);
        if (startpoint > 0 && startpoint <= Integer.MAX_VALUE) {
            if (editingCue != null) {
                editingCue.setCueStartPoint((int) startpoint);
            }
        } else {
            nmrCueStartPoint.setText("0");
        }
    }

    private void changeCuePlayDelay(String newValue) {
        newValue = newValue.replaceAll("[^\\d]", "");
        if (newValue.isEmpty()) {
            nmrCuePlayDelay.setText("");
            return;
        }
        nmrCuePlayDelay.setText(newValue);

        long delay = Long.parseLong(newValue);
        if (delay >= 0 && delay <= Integer.MAX_VALUE) {
            if (editingCue != null) {
                editingCue.setCuePlayDelay((int) delay);
            }
        } else {
            if (delay < 0) {
                nmrCuePlayDelay.setText("0");
            } else {
                nmrCuePlayDelay.setText("" + Integer.MAX_VALUE);
            }
        }
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file for cue");
        //fileChooser.setInitialDirectory(((FileCue) editingCue).getFilePath().getParent().toFile());
        File file = fileChooser.showOpenDialog(btnChooseFile.getScene().getWindow());
        if (file != null) {
            changeFilePath(file);
        }
    }

    private void changeFilePath(File file) {
        ((FileCue) editingCue).setCueFilePath(file.getAbsolutePath());
        updateFieldEntries(true);
    }

    private void changeCueBehaviour() {
        editingCue.setCueBehaviour((CueBehaviour) cmbCueBehaviour.getSelectionModel().getSelectedItem());
    }

    //todo reorder methods to be more organised

    private void changeCueName() {
        editingCue.setCueName(txtCueName.getText());
    }

    private void closeEditCueWindow() {
        Stage thisStage = (Stage) btnCancelChanges.getScene().getWindow();
        thisStage.close();
    }

    private void closeAfterReturningCue() {
        if (cueIsToAdd) {
            parentController.addNewCue(editingCue);
        } else {
            parentController.setEditedCue(editingCue);
        }
        closeEditCueWindow();
    }

    private void setSizes() {
        //allow growing
        btnSaveChanges.setMaxWidth(Double.MAX_VALUE);
        btnCancelChanges.setMaxWidth(Double.MAX_VALUE);
        lblCueInfo.setMaxWidth(Double.MAX_VALUE);
        lblCueType.setMaxWidth(Double.MAX_VALUE);
        lblCueName.setMaxWidth(Double.MAX_VALUE);
        lblCueBehaviour.setMaxWidth(Double.MAX_VALUE);
        container_file_chooser.setMaxWidth(Double.MAX_VALUE);
        nmrCuePlayDelay.setMaxWidth(130);
        nmrCueStartPoint.setMaxWidth(130);
        nmrCueDuration.setMaxWidth(130);
        lblFilePath.setMaxWidth(Double.MAX_VALUE);
        lblMS.setMaxWidth(Double.MAX_VALUE);
        lblMS2.setMaxWidth(Double.MAX_VALUE);
        lblMS3.setMaxWidth(Double.MAX_VALUE);

        //ensure grid pane always fill width of parent
        AnchorPane.setTopAnchor(grid_pane, .0);
        AnchorPane.setBottomAnchor(grid_pane, .0);
        AnchorPane.setLeftAnchor(grid_pane, .0);
        AnchorPane.setRightAnchor(grid_pane, .0);

        //make grid columns wide as parent
        ColumnConstraints columnConstraintForHalfWidth = new ColumnConstraints();
        columnConstraintForHalfWidth.setPercentWidth(50);
        grid_pane.getColumnConstraints().addAll(columnConstraintForHalfWidth, columnConstraintForHalfWidth);

        //set HGrow
        HBox.setHgrow(lblFilePath, Priority.ALWAYS);
        HBox.setHgrow(nmrCuePlayDelay, Priority.NEVER);
        HBox.setHgrow(nmrCueStartPoint, Priority.NEVER);
        HBox.setHgrow(nmrCueDuration, Priority.NEVER);
        HBox.setHgrow(lblMS, Priority.ALWAYS);
        HBox.setHgrow(lblMS2, Priority.ALWAYS);
        HBox.setHgrow(lblMS3, Priority.ALWAYS);

        //update layouts
        anchor_pane.layout();
        grid_pane.layout();
    }

    public void setParentController(FormMainController c) {
        this.parentController = c;
    }

    public void setEditObject(Cue c) {
        if (c != null) {
            try {
                editingCue = c.getClass().newInstance();
                editingCue.setIndex(c.getIndex());
                editingCue.setCueName(c.getCueName());
                editingCue.setCueBehaviour(c.cueBehaviourEnum);
                editingCue.setCueFilePath(Paths.get(c.getCueFilePath()));
                editingCue.setCuePlayDelay(c.getCuePlayDelay());
                editingCue.setCueStartPoint(c.getCueStartPoint());
                editingCue.setCueDuration(c.getCueDuration());
            } catch (Exception ex) {
                ex.printStackTrace();
                editingCue = new UnknownCue();
                editingCue.setIndex(parentController.getCueCollectionSize());
            }
        } else {
            editingCue = new UnknownCue();
            editingCue.setIndex(parentController.getCueCollectionSize());
        }
        updateFieldEntries(true);
    }

    public void setCueIsToAdd(boolean b) {
        cueIsToAdd = b;
    }

    private void updateFieldEntries(boolean changeCueTypeComboBox) {
        lblCueInfo.setText(editingCue.toString());
        txtCueName.setText(editingCue.getCueName());
        if (changeCueTypeComboBox) {
            // DO NOT CHANGE COMBOBOX WHEN THIS METHOD IS CALLED DUE TO A CHANGE IN THE COMBO BOX
            cmbCueType.getSelectionModel().select(editingCue.cueTypeEnum);
        }
        cmbCueBehaviour.getSelectionModel().select(editingCue.cueBehaviourEnum);
        boolean cueTypeUsesFile = editingCue instanceof FileCue;
        if (cueTypeUsesFile) {
            lblFilePath.setText(((FileCue) editingCue).getFilePath().toAbsolutePath().toString());
        } else {
            lblFilePath.setText("No file");
        }
        container_file_chooser.setDisable(!cueTypeUsesFile);
        nmrCuePlayDelay.setText("" + editingCue.getCuePlayDelay());
        nmrCueStartPoint.setText("" + editingCue.getCueStartPoint());
        nmrCueDuration.setText("" + editingCue.getCueDuration());
    }

    private void changeCueType() {
        int ind = editingCue.getIndex();
        boolean wasFile = editingCue instanceof FileCue;
        switch ((CueType) cmbCueType.getSelectionModel().getSelectedItem()) {
            case UNKNOWN:
                editingCue = new UnknownCue();
                break;
            case SOUND:
                editingCue = new SoundCue();
                break;
            case LIGHT:
                //todo make light cue a thing
                throw new NotImplementedException(); //break;
            case VIDEO:
                editingCue = new VideoCue();
                break;
            case VOICE:
                editingCue = new VoiceCue();
                break;
            case BASH:
                editingCue = new BashCue();
                break; //NB: remember to break here, as otherwise things literally break.
            default:
                System.out.println("Tried to create cue that isn't accounted for!");
                throw new NotImplementedException();
        }
        editingCue.setIndex(ind);
        editingCue.setCueName(txtCueName.getText());
        editingCue.setCueBehaviour((CueBehaviour) cmbCueBehaviour.getSelectionModel().getSelectedItem());
        if (wasFile && editingCue instanceof FileCue) {
            ((FileCue) editingCue).setCueFilePath(lblFilePath.getText());
        }
        editingCue.setCuePlayDelay(Integer.parseInt(nmrCuePlayDelay.getText()));
        editingCue.setCueStartPoint(Integer.parseInt(nmrCueStartPoint.getText()));
        editingCue.setCueDuration(Integer.parseInt(nmrCueDuration.getText()));

        updateFieldEntries(false);
    }
}
