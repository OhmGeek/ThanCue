package ThanCue.Forms;

import ThanCue.*;
import com.sun.media.jfxmedia.control.VideoFormat;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormMainController {
    private static final List<String> soundExtensions = new ArrayList<String>() {{
        add("mp3");
        add("wav");
        add("ogg");

    }};

    //Container panes
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private GridPane grid_pane;
    @FXML
    private GridPane top_row_button_container;

    //Dev menu
    @FXML
    private MenuItem btnPrintCues;

    //File menu
    @FXML
    private MenuItem btnNew;
    @FXML
    private MenuItem btnOpen;
    @FXML
    private MenuItem btnSave;
    @FXML
    private MenuItem btnSaveAs;
    @FXML
    private CheckMenuItem chkShowMode;
    @FXML
    private MenuItem btnExit;

    //Edit menu
    @FXML
    private MenuItem btnUndo;
    @FXML
    private MenuItem btnRedo;

    //Help menu
    @FXML
    private MenuItem btnAbout;

    //Cue info view
    @FXML
    private TableView<Cue> tblView;

    //Form buttons
    @FXML
    private Button btnGo;
    @FXML
    private Button btnMoveUp;
    @FXML
    private Button btnMoveDown;
    @FXML
    private Button btnAddCue;
    @FXML
    private Button btnEditCue;

    private ObservableList<Cue> cueCollection = FXCollections.observableArrayList(
            new SoundCue(),
            new SoundCue(),
            new UnknownCue(),
            new VideoCue(),
            new UnknownCue(),
            new SoundCue() //todo start empty (or loaded from file)
    );

    @FXML
    public void initialize() {
        System.out.println("Let's get this party started!");
        setActions();
        setSizes();
        setTableData();
        registerDragAndDrop();
    }

    public void onCloseRequest() {
        for(Cue c : cueCollection)
            c.stopCue();
    }

    private void registerDragAndDrop() {
        for (Node c : anchor_pane.getChildren()) {
            if (c instanceof Region) {
                Region r = (Region) c;

                r.setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.ANY);
                });

                r.setOnDragDropped(event -> {
                    System.out.println("DRAGGING AND DROPPIN'");
                    Dragboard db = event.getDragboard();
                    if (db.hasFiles()) {
                        List<File> fileList = db.getFiles();
                        for (File f : fileList) {
                            System.out.println("File to add: " + f.getName());
                            String ext = f.getName().substring(f.getName().length() - 3, f.getName().length());
                            System.out.println("Found extension: " + ext);



                            //todo put in a nice method to make more readable
                            try {
                                AudioFileFormat foramt = AudioSystem.getAudioFileFormat(f);
                                SoundCue cToAdd = new SoundCue();
                                cToAdd.setCueName(f.getName());
                                cToAdd.setCueFilePath(f.getAbsolutePath());
                                cueCollection.add(cToAdd);
                            }
                            catch(Exception ex) {
                                System.out.println("Not a sound cue");
                            }

                            if(ext.contains("mp4") || ext.contains("m4v")) {
                                VideoCue cToAdd = new VideoCue();
                                cToAdd.setCueName(f.getName());
                                cToAdd.setCueFilePath(f.getAbsolutePath());
                                cueCollection.add(cToAdd);
                            }


                        }
                        event.setDropCompleted(true);
                    }
                    refreshTable();
                    event.consume();
                });
            }
        }
    }

    private void refreshTable() {
        //update indexes
        for (int i = 0; i < cueCollection.size(); i++) {
            Cue c = cueCollection.get(i);
            c.setIndex(i);
            cueCollection.set(i, c); // not needed because pointers :D
        }

        tblView.refresh();
    }

    private void setTableData() {
        //create columns
        TableColumn<Cue, Integer> clmIndex = new TableColumn<>("Index");
        clmIndex.setSortable(false);
        TableColumn clmType = new TableColumn("Type");
        clmType.setSortable(false);
        TableColumn clmName = new TableColumn("Name");
        clmName.setSortable(false);
        TableColumn clmBehaviour = new TableColumn("Behaviour");
        clmBehaviour.setSortable(false);
        TableColumn clmFilePath = new TableColumn("File path");
        clmFilePath.setSortable(false);
        TableColumn<Cue, Integer> clmDelay = new TableColumn<>("Start delay (ms)");
        clmDelay.setSortable(false);

        //set cell 'renderers'
        clmIndex.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIndex()).asObject());
        //supposedly the normal way works, however, in practice it absolutely does not... Oh well, this will do.
        clmIndex.setCellFactory(param -> { //purely for alignment
            TableCell<Cue, Integer> cell = new TableCell<Cue, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    if (item != null) {
                        setText(item.toString());
                    }
                }
            };
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        clmType.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueType"));
        clmType.setCellFactory(param -> {
            TableCell<Cue, String> cell = new TableCell<Cue, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        super.updateItem(item, empty);
                        HBox box = new HBox();
                        box.setSpacing(10);

                        Label typeName = new Label(item);
                        ImageView imageview = Cue.getImageView(item);

                        box.getChildren().addAll(imageview, typeName);
                        setGraphic(box);

                    }

                }
            };
            return cell;
        });

        clmName.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueName"));

        clmBehaviour.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueBehaviour"));

        clmFilePath.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueFilePath"));

        clmDelay.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCuePlayDelay()).asObject());
        //supposedly the normal way works, however, in practice it absolutely does not... Oh well, this will do.
        clmDelay.setCellFactory(param -> { //purely for alignment, delete entire cell factory if you don't care about alignment (but I do :D)
            TableCell<Cue, Integer> cell = new TableCell<Cue, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    if (item != null) {
                        setText(item.toString());
                    }
                }
            };
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        //add columns
        tblView.getColumns().addAll(clmIndex, clmType, clmName, clmBehaviour, clmFilePath, clmDelay);

        //link data to table
        tblView.setItems(cueCollection);

        //show data
        refreshTable();
    }

    private void setSizes() {
        //allow growing
        btnAddCue.setMaxWidth(Double.MAX_VALUE);
        btnEditCue.setMaxWidth(Double.MAX_VALUE);
        btnMoveUp.setMaxWidth(Double.MAX_VALUE);
        btnMoveDown.setMaxWidth(Double.MAX_VALUE);
        btnGo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnGo.setPrefHeight(140);
        btnGo.setFont(new Font(btnGo.getFont().getFamily(), 30));

        //ensure grid pane always fill width of parent
        AnchorPane.setTopAnchor(grid_pane, .0);
        AnchorPane.setBottomAnchor(grid_pane, .0);
        AnchorPane.setLeftAnchor(grid_pane, .0);
        AnchorPane.setRightAnchor(grid_pane, .0);

        //make grid column wide as parent
        ColumnConstraints columnConstraintForMaxWidth = new ColumnConstraints();
        columnConstraintForMaxWidth.setHgrow(Priority.ALWAYS);
        columnConstraintForMaxWidth.setFillWidth(true);
        grid_pane.getColumnConstraints().add(columnConstraintForMaxWidth);
        ColumnConstraints percent25Width = new ColumnConstraints();
        percent25Width.setPercentWidth(25);
        top_row_button_container.getColumnConstraints().addAll(percent25Width, percent25Width, percent25Width, percent25Width);

        //make grid pane tall as parent (on a row by row basis)
        RowConstraints rowConstraintNoGrow = new RowConstraints();
        RowConstraints rowConstraintMaybeGrow = new RowConstraints();
        RowConstraints rowConstraintGrow = new RowConstraints();
        rowConstraintNoGrow.setVgrow(Priority.NEVER);
        rowConstraintMaybeGrow.setVgrow(Priority.SOMETIMES);
        rowConstraintGrow.setVgrow(Priority.ALWAYS);
        grid_pane.getRowConstraints().add(rowConstraintNoGrow);
        grid_pane.getRowConstraints().add(rowConstraintNoGrow);
        grid_pane.getRowConstraints().add(rowConstraintGrow);
        grid_pane.getRowConstraints().add(rowConstraintMaybeGrow);

        //make btnGo grow
        GridPane.setFillWidth(btnGo, true);
        GridPane.setFillHeight(btnGo, true);
        GridPane.setHgrow(btnAddCue, Priority.ALWAYS);
        GridPane.setHgrow(btnEditCue, Priority.ALWAYS);
        GridPane.setHgrow(btnMoveUp, Priority.ALWAYS);
        GridPane.setHgrow(btnMoveDown, Priority.ALWAYS);

        //update layouts
        anchor_pane.layout();
        grid_pane.layout();
        tblView.layout();
        btnGo.layout();
    }

    private void setActions() {
        btnPrintCues.setOnAction(event -> printAllCues());

        //File Menu
        btnNew.setOnAction(event -> System.out.println("New Cue Stack"));
        btnOpen.setOnAction(event -> System.out.println("Open Cue Stack"));
        btnSave.setOnAction(event -> System.out.println("Save Cue Stack"));
        btnSaveAs.setOnAction(event -> System.out.println("Save As"));
        chkShowMode.setOnAction(event -> toggleShowMode());
        btnAbout.setOnAction(event -> showAbout());
        btnExit.setOnAction(event -> Platform.exit());

        //Edit Menu
        btnUndo.setOnAction(event -> System.out.println("Undo"));
        btnRedo.setOnAction(event -> System.out.println("Redo"));

        //Form Buttons
        btnGo.setOnAction(event -> playSelectedCue());
        btnAddCue.setOnAction(event -> addNewCue());
        btnEditCue.setOnAction(event -> editSelectedCue());
        btnMoveUp.setOnAction(event -> moveSelectedCueUp());
        btnMoveDown.setOnAction(event -> moveSelectedCueDown());

        //Table
        tblView.getSelectionModel().selectedItemProperty().addListener((observableValue, cue, t1) -> selectionChanged());
    }

    private void showAbout() {
        FXMLLoader root;
        try {
            root = new FXMLLoader(getClass().getResource("FormAbout.fxml"));
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(anchor_pane.getScene().getWindow());
            stage.setScene(new Scene(root.load(), 400, 350));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectionChanged() {
        btnGo.setDisable(tblView.getSelectionModel().getSelectedItem() == null);
    }

    private void toggleShowMode() {
        top_row_button_container.setDisable(chkShowMode.isSelected());
    }

    private void playSelectedCue() {
        // NOTE: I changed this a fair bit, as we only want to have ONE cue selected at once, and use behaviour to play many cues
        // as such, todo use behaviour to play more than the one selected (while next one is play on/after this?)
        Cue cue = tblView.getSelectionModel().getSelectedItem();
        if (cue instanceof SoundCue) { // todo is this check for testing? probably not needed...
            cue.playCue();
        }
        int currentCueIndex = tblView.getSelectionModel().getSelectedIndex();

        //todo below is untested
        while (currentCueIndex + 1 < cueCollection.size() && cueCollection.get(currentCueIndex + 1).cueBehaviourEnum == CueBehaviour.PLAY_WITH_PREVIOUS) {
            currentCueIndex++;
            cue = cueCollection.get(currentCueIndex);
            cue.playCue();
        }
        //todo also somehow account for play after previous?

        currentCueIndex++;
        if (currentCueIndex == cueCollection.size()) {
            if (chkShowMode.isSelected()) {
                //DO NOT LOOP AROUND in show mode
                tblView.getSelectionModel().clearSelection();
            } else {
                //do loop around out of show mode
                tblView.getSelectionModel().select(0);
            }
        } else {
            //not at the end, ie. not ready to try and loop yet
            tblView.getSelectionModel().select(currentCueIndex);
        }
    }

    private void addNewCue() {
        showEditForm(null, true);
    }

    private void editSelectedCue() {
        if (tblView.getSelectionModel().getSelectedCells().size() > 0) {
            Cue c = tblView.getSelectionModel().getSelectedItem();
            showEditForm(c, false);
        } else {
            showDialogNothingSelected();
        }
    }

    private void showEditForm(Cue cueToEdit, boolean cueIsToAdd) {
        FXMLLoader root;
        try {
            root = new FXMLLoader(getClass().getResource("FormEditCue.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Cue");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(anchor_pane.getScene().getWindow());
            stage.setScene(new Scene(root.load(), 400, 350));
            root.<FormEditCueController>getController().setCueIsToAdd(cueIsToAdd);
            root.<FormEditCueController>getController().setParentController(this);
            root.<FormEditCueController>getController().setEditObject(cueToEdit);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewCue(Cue cueToAdd) {
        cueCollection.add(cueToAdd);
        refreshTable();
    }

    public void setEditedCue(Cue editedCue) {
        cueCollection.set(editedCue.getIndex(), editedCue);
        refreshTable();
    }

    private void printAllCues() {
        System.out.println("\nCues:");
        for (Cue cue : cueCollection) {
            System.out.print("\t");
            cue.print();
        }
    }

    private void moveSelectedCueUp() {
        if (tblView.getSelectionModel().getSelectedCells().size() > 0) {
            int firstSelection = tblView.getSelectionModel().getSelectedIndex();
            if (firstSelection > 0) { //if not already at the top
                Cue swapDown = cueCollection.get(firstSelection - 1);
                Cue swapUp = cueCollection.get(firstSelection);
                swapDown.setIndex(swapDown.getIndex() + 1);
                swapUp.setIndex(swapUp.getIndex() - 1);
                cueCollection.set(firstSelection - 1, swapUp);
                cueCollection.set(firstSelection, swapDown);
                tblView.getSelectionModel().clearAndSelect(firstSelection - 1);
            }
        } else {
            showDialogNothingSelected();
        }
    }

    private void moveSelectedCueDown() {
        if (tblView.getSelectionModel().getSelectedCells().size() > 0) {
            int firstSelection = tblView.getSelectionModel().getSelectedIndex();
            if (firstSelection < cueCollection.size() - 1) { //if not already at the bottom
                Cue swapDown = cueCollection.get(firstSelection);
                Cue swapUp = cueCollection.get(firstSelection + 1);
                swapDown.setIndex(swapDown.getIndex() + 1);
                swapUp.setIndex(swapUp.getIndex() - 1);
                cueCollection.set(firstSelection, swapUp);
                cueCollection.set(firstSelection + 1, swapDown);
                tblView.getSelectionModel().clearAndSelect(firstSelection + 1);
            }
        } else {
            showDialogNothingSelected();
        }
    }

    private void showDialogNothingSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You must have a cue selected to do that!", ButtonType.OK);
        alert.setHeaderText("Selection issue");
        alert.showAndWait();
    }

    public int getCueCollectionSize() {
        return cueCollection.size();
    }
}

