package ThanCue;

import com.briksoftware.updatefx.core.UpdateFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));

        primaryStage.setTitle("ThanCue " + Constants.VERSION_NAME + " (" + Constants.RELEASE_ID + ")");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();

        Platform.runLater(() -> {
            // todo check that this now works to update (moved it into Platform.runLater, didn't test yet)
            //check for updates
            System.out.println("Checking for updates...");
            try {
                UpdateFX updater = new UpdateFX(Main.class);
                updater.checkUpdates();
            } catch (IOException ex) {
                System.out.println("Updating failed");
                ex.printStackTrace();
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
