package ThanCue.Forms;

import ThanCue.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static ThanCue.CueType.UNSET;

/**
 * Created by mike on 04/06/16.
 */
public class FormEditCueController {
    // todo the rest of this class and stuff

    private FormMainController parentController;
    private Cue editingCue;

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

    //Selectors
    @FXML
    private ComboBox cmbCueType;
    @FXML
    private ComboBox cmbCueBehaviour;

    //Text areas
    @FXML
    private TextField txtCueName;

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
        cmbCueType.getSelectionModel().select(UNSET);
        cmbCueBehaviour.getItems().setAll(CueBehaviour.values());
        cmbCueBehaviour.getSelectionModel().select(CueBehaviour.PLAY_ON_GO);
    }

    private void setActions() {
        //buttons
        btnCancelChanges.setOnAction(event -> closeEditCueWindow());
        btnSaveChanges.setOnAction(event -> closeAfterReturningCue());
        btnChooseFile.setOnAction(event -> chooseFile());

        //fields
        cmbCueType.setOnAction(event -> changeCueType());
        txtCueName.textProperty().addListener((observableValue, s, t1) -> changeCueName()); //text changed
        cmbCueBehaviour.setOnAction(event -> changeCueBehaviour());
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file for cue");
        fileChooser.setInitialDirectory(((FileCue)editingCue).getFilePath().getParent().toFile());
        File file = fileChooser.showOpenDialog(btnChooseFile.getScene().getWindow());
        if(file != null){
            changeFilePath(file);
        }
    }

    private void changeFilePath(File file) {
        ((FileCue)editingCue).setCueFilePath(file.getAbsolutePath());
        updateFieldEntries(true);
    }

    private void changeCueBehaviour() {
        editingCue.setCueBehaviour((CueBehaviour)cmbCueBehaviour.getSelectionModel().getSelectedItem());
    }

    //todo reorder methods to be more organised

    private void changeCueName() {
        editingCue.setCueName(txtCueName.getText());
    }

    private void closeEditCueWindow() {
        Stage thisStage = (Stage)btnCancelChanges.getScene().getWindow();
        thisStage.close();
    }

    private void closeAfterReturningCue() {
        parentController.setEditedCue(editingCue);
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
        lblFilePath.setMaxWidth(Double.MAX_VALUE);

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

        //update layouts
        anchor_pane.layout();
        grid_pane.layout();
    }

    public void setParentController(FormMainController c){
        this.parentController = c;
    }

    public void setEditObject(Cue c) {
        if (c != null) {
            editingCue = c;
        } else {
            editingCue = new UnknownCue();
        }
        updateFieldEntries(true);
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
        if(cueTypeUsesFile){
            lblFilePath.setText(((FileCue)editingCue).getFilePath().toAbsolutePath().toString());
        }else{
            lblFilePath.setText("No file");
        }
        container_file_chooser.setDisable(!cueTypeUsesFile);
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
                c = new VideoCue();
                break;
        }
        c.setCueName(txtCueName.getText());
        c.setCueBehaviour((CueBehaviour)cmbCueBehaviour.getSelectionModel().getSelectedItem());
        if(c instanceof FileCue){
            if(editingCue instanceof FileCue){ // this could cause errors so ALWAYS TRY CATCH PLAYING A FILE CUE
                ((FileCue) c).setCueFilePath(((FileCue) editingCue).getFilePath().toAbsolutePath().toString());
            }else {
                ((FileCue) c).setCueFilePath(lblFilePath.getText());
            }
        }
        editingCue = c;
        updateFieldEntries(false);
    }
}
