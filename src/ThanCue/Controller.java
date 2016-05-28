package ThanCue;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class Controller {
    @FXML
    private ScrollPane the_pane;

    private AddListener addListener = new AddListener();

    private class AddListener implements EventHandler<ActionEvent> {
        public void handle(ActionEvent ae) {
            frmMain f = new frmMain();
            System.out.println("HANDLE STUFF");

            SwingNode sn = new SwingNode();
            sn.setContent(f.getPanel());
            Platform.runLater(() -> {
                the_pane.setContent(sn);
                System.out.println("SET THE FUCKING CONTENT");
            });
        }

    }


}
