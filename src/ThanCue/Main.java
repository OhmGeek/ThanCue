package ThanCue;

import com.briksoftware.updatefx.core.UpdateFX;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by ryan on 15/05/16.
 */

public class Main {
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

        JFrame window = new JFrame("ThanCue v0.5");
        frmMain mainPanel = new frmMain(window);

        window.setContentPane(mainPanel.getPanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}
