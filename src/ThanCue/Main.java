package ThanCue;

import com.briksoftware.updatefx.core.UpdateFX;
import com.briksoftware.updatefx.model.Application;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by ryan on 15/05/16.
 */

public class Main {
    public static final String VERSION_NAME = "ScriptMade";

    public static void main(String[] args) {
        try {
            new JFXPanel();
            Platform.runLater(() -> {
                // todo hmm... do I need to do something here?
            });

            UpdateFX updater = new UpdateFX(Main.class);
            updater.checkUpdates();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Running on OS: " + System.getProperty("os.name").toLowerCase());

        Platform.setImplicitExit(false);

        Platform.runLater(() -> {
            Stage window = new Stage();
            frmMain mainForm = new frmMain();
            Pane p = mainForm.load();
            System.out.println("IVE DONE SOMETHING DADDY!");
            window.setScene(new Scene(p,500,500));
            window.showAndWait();
        });

    }
}
