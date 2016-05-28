package ThanCue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Controller {
    private Random r; // purely for adding test cues todo remove

    //dev menu
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

    //edit menu
    @FXML
    private MenuItem btnUndo;
    @FXML
    private MenuItem btnRedo;
    @FXML
    private TableView<Cue> tblView;

    //form buttons
    @FXML
    private Button btnGo;




    @FXML
    public void initialize() {
        System.out.println("second");
        setActions();
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

