package ThanCue;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private MenuItem btnExit;

    //Edit menu
    @FXML
    private MenuItem btnUndo;
    @FXML
    private MenuItem btnRedo;

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

    ObservableList<Cue> cueCollection = FXCollections.observableArrayList(
            new SoundCue(),
            new SoundCue(),
            new UnknownCue(),
            new UnknownCue(),
            new UnknownCue(),
            new SoundCue()
    );


    @FXML
    public void initialize() {
        System.out.println("Let's get this party started!");
        setActions();
        setSizes();
        setTableData();
        registerDragAndDrop();
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
                            //todo BETTER checking to the type. Only allow for sound cues or videos (or cue stacks?).
                            if (soundExtensions.contains(ext)) {
                                SoundCue cToAdd = new SoundCue();
                                cToAdd.setCueName(f.getName());
                                cToAdd.setFilePath(f.getAbsolutePath());
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
            //cueCollection.set(i, c); // not needed because pointers :D
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

        //set cell 'renderers'
        clmIndex.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIndex()).asObject());
        //supposedly the normal way works, however, in practice it absolutely does not... Oh well, this will do.

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


        //add columns
        tblView.getColumns().addAll(clmIndex, clmType, clmName, clmBehaviour);


        //link data to table
        tblView.setItems(cueCollection);
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
        HBox.setHgrow(btnAddCue, Priority.ALWAYS);
        HBox.setHgrow(btnEditCue, Priority.ALWAYS);
        HBox.setHgrow(btnMoveUp, Priority.ALWAYS);
        HBox.setHgrow(btnMoveDown, Priority.ALWAYS);

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
        btnExit.setOnAction(event -> Platform.exit());

        //Edit Menu
        btnUndo.setOnAction(event -> System.out.println("Undo"));
        btnRedo.setOnAction(event -> System.out.println("Redo"));

        //Form Buttons
        btnGo.setOnAction(event -> {
            List<Cue> selectedCuesToPlay = tblView.getSelectionModel().getSelectedItems();
            selectedCuesToPlay.forEach(cue -> {
                if (cue instanceof SoundCue)
                    cue.playCue();
            });
        });
        btnAddCue.setOnAction(event -> addNewCue());
        btnEditCue.setOnAction(event -> editSelectedCue());
        btnMoveUp.setOnAction(event -> moveSelectedCueUp());
        btnMoveDown.setOnAction(event -> moveSelectedCueDown());
    }

    private void addNewCue() {
        // todo how do we get the edited (was blank) cue back from the controller? is the cue passing purely a reference?
        FXMLLoader root;
        try {
            root = new FXMLLoader(getClass().getResource("FormEditCue.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Cue");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(anchor_pane.getScene().getWindow());
            stage.setScene(new Scene(root.load(), 400, 350));
            root.<FormEditCueController>getController().setEditObject(null);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editSelectedCue() {
        if (tblView.getSelectionModel().getSelectedCells().size() > 0) {
            // todo how do we get the edits back from the controller? is the cue passing purely a reference?
            //get cue and pass to dialog
            int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
            Cue c = tblView.getSelectionModel().getSelectedItem();
            FXMLLoader root;
            try {
                root = new FXMLLoader(getClass().getResource("FormEditCue.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Edit Cue");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(anchor_pane.getScene().getWindow());
                stage.setScene(new Scene(root.load(), 400, 350));
                root.<FormEditCueController>getController().setEditObject(c);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showDialogNothingSelected();
        }
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
            int firstSelection = tblView.getSelectionModel().getSelectedIndex(); //todo support multiple cues to move at once
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
            int firstSelection = tblView.getSelectionModel().getSelectedIndex(); //todo support multiple cues to move at once
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
}

