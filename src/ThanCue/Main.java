package ThanCue;

import ThanCue.Variables.Constants;
import ThanCue.Variables.Environment;
import com.briksoftware.updatefx.core.UpdateFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load up details about the Environment :)
        Environment.tempDirectories = new ArrayList<>();
        Environment.operatingSystem = System.getProperty("os.name");

        Parent root = FXMLLoader.load(getClass().getResource("Forms/FormMain.fxml"));

        primaryStage.setTitle("ThanCue " + Constants.VERSION_NAME + " (" + Constants.RELEASE_ID + ")"); // todo stop showing release ID (only for dev)
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/appicon.png"))); // todo get this to work more than 5% of the time
        primaryStage.show();



        //we go through the list of temporary directories that have been opened and delete them!
        //Only do this when the main window itself has closed.
        primaryStage.setOnCloseRequest(we -> {
            Environment.tempDirectories.forEach(file -> file.delete());
        });



        Platform.runLater(() -> {
            // todo fix updateFX files, as currently updating runs a temp copy of the new version, does not replace the old version
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
