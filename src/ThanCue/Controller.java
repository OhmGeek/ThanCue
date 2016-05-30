package ThanCue;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;

import javax.swing.*;
import java.util.Random;

public class Controller {
    private Random r; // purely for adding test cues todo remove

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

    ObservableList<Cue> cueCollection = FXCollections.observableArrayList(
            new SoundCue(),
            new SoundCue()
    );


    @FXML
    public void initialize() {
<<<<<<< HEAD
        System.out.println("Let's get this party started!");
=======
        System.out.println("Initialising fxml controller");
>>>>>>> 7710778240bab5b1318e165d260da6cb54e43f21
        setActions();
        setSizes();
        setTableData();
    }

    private void setTableData() {
        TableColumn<Cue, Integer> clmIndex = new TableColumn<>("Index");
        TableColumn<Cue, ImageIcon> clmIcon = new TableColumn<>("Icon");
        TableColumn clmType = new TableColumn("Type");
        TableColumn clmName = new TableColumn("Name");
        TableColumn clmBehaviour = new TableColumn("Behaviour");

        clmIndex.setCellValueFactory(new PropertyValueFactory<Cue, Integer>("index"));
        clmIcon.setCellFactory(new Callback<TableColumn<Cue, ImageIcon>, TableCell<Cue, ImageIcon>>() {
            @Override
            public TableCell<Cue, ImageIcon> call(TableColumn<Cue, ImageIcon> col) {
                return new TableCell<Cue, ImageIcon>() {
                    @Override
                    protected void updateItem(ImageIcon ico, boolean empty) {
                        super.updateItem(ico, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (ico == null){
                                setText("ImageIcon ico is null");
                            }else {
                                setText("Image found " + ico.toString());
                            }
                        }
                    }
                };
            }
        });
        clmType.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueType"));


        tblView.getColumns().addAll(clmIndex, clmIcon, clmType, clmName, clmBehaviour);

        tblView.setItems(cueCollection);
    }

    private void setSizes() {
        //allow growing
        btnGo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnGo.setPrefHeight(140);

        //ensure grid pane always fill width of parent
        AnchorPane.setTopAnchor(grid_pane, .0);
        AnchorPane.setBottomAnchor(grid_pane, .0);
        AnchorPane.setLeftAnchor(grid_pane, .0);
        AnchorPane.setRightAnchor(grid_pane, .0);

        //make grid pane wide as parent
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
        grid_pane.getRowConstraints().add(rowConstraintGrow);
        grid_pane.getRowConstraints().add(rowConstraintMaybeGrow);

        //make btnGo grow
        GridPane.setFillWidth(btnGo, true);
        GridPane.setFillHeight(btnGo, true);

        //update layouts
        anchor_pane.layout();
        grid_pane.layout();
        tblView.layout();
        btnGo.layout();
    }

    private void setActions() {
        btnPrintCues.setOnAction(event -> {

        });

        //File Menu
        btnNew.setOnAction(event -> System.out.println("New Cue"));
        btnOpen.setOnAction(event -> System.out.println("Open cue stack"));
        btnSave.setOnAction(event -> System.out.println("Save Cue Stack"));
        btnSaveAs.setOnAction(event -> System.out.println("Save As"));
        btnExit.setOnAction(event -> Platform.exit());

        //Edit Menu
        btnUndo.setOnAction(event -> System.out.println("Undo"));
        btnRedo.setOnAction(event -> System.out.println("Redo"));

        //Form Buttons
        btnGo.setOnAction(event -> System.out.println("Play a cue"));
    }
}

