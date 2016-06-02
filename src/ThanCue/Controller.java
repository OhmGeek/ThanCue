package ThanCue;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

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
    }

    // todo where are all the lines of "-1" printing from?!

    private void setTableData() {
        //update indexes
        for (int i = 0; i < cueCollection.size(); i++){
            Cue c = cueCollection.get(i);
            c.setIndex(i);
            cueCollection.set(i, c);
            System.out.println("Setting id of cue " + i); // todo remove entire loop (only for testing (I think))
        }

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
        clmType.setCellFactory((Callback<TableColumn<Cue, String>, TableCell<Cue, String>>) param -> {
            TableCell<Cue, String> cell = new TableCell<Cue, String>(){
                @Override
                public void updateItem(String item, boolean empty) {
                    if(item!=null){
                        HBox box = new HBox();
                        box.setSpacing(10) ;

                        Label typeName = new Label(item);
                        ImageView imageview = Cue.getImageView(item);

                        box.getChildren().addAll(imageview, typeName);
                        setGraphic(box);
                    }
                }
            };
            System.out.println(cell.getIndex());
            return cell;
        });

        clmName.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueName"));

        clmBehaviour.setCellValueFactory(new PropertyValueFactory<Cue, String>("cueBehaviour"));


        //add columns
        tblView.getColumns().addAll(clmIndex, clmType, clmName, clmBehaviour);

        //link data to table
        tblView.setItems(cueCollection);
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
        btnPrintCues.setOnAction(event -> {
            System.out.println("\nCues:");
            for (Cue cue : cueCollection) {
                System.out.print("\t");
                cue.print();
            }
        });

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
        btnGo.setOnAction(event -> System.out.println("Play a cue"));
        btnAddCue.setOnAction(event -> System.out.println("Add a cue"));
        btnEditCue.setOnAction(event -> System.out.println("Edit this cue"));
        btnMoveUp.setOnAction(event -> System.out.println("Move this cue up"));
        btnMoveDown.setOnAction(event -> System.out.println("Move this cue down"));
    }
}

