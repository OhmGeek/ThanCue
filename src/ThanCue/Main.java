package ThanCue;

import com.briksoftware.updatefx.core.UpdateFX;
import com.sun.applet2.preloader.event.ApplicationExitEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //I'm not so keen on this, so todo make this nicer (in terms of structure)
        Environment.tempDirectories = new ArrayList<File>();

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
