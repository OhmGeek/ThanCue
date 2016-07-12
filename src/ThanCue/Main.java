package ThanCue;

import ThanCue.Variables.Constants;
import ThanCue.Variables.Environment;
import com.briksoftware.updatefx.core.UpdateFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.zeroturnaround.zip.commons.FileUtils;
import sun.awt.OSInfo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {

    private static final String IMG_ICO_PNG = "/img/ico.png"; // todo move to constants
    private static Image icon16;
    private static Image icon22;
    private static Image icon24;
    private static Image icon32;
    private static Image icon48;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Load up details about the Environment :)
        Environment.tempDirectories = new ArrayList<>();
        Environment.operatingSystem = OSInfo.getOSType();

        Parent root = FXMLLoader.load(getClass().getResource("Forms/FormMain.fxml"));

        primaryStage.getIcons().addAll(icon16, icon22, icon24, icon32, icon48); // todo get this to work more than 5% of the time
        primaryStage.setTitle("ThanCue " + Constants.VERSION_NAME + " (" + Constants.RELEASE_ID + ")"); // todo maybe stop showing release ID (only for dev)
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

        //we go through the list of temporary directories that have been opened and delete them!
        //Only do this when the main window itself has closed.
        primaryStage.setOnCloseRequest(we -> {
            Environment.tempDirectories.forEach(file -> {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException ex1) {
                    System.out.println("IO Exception occurs");
                    //todo handle exception
                } catch (Exception ex2) {
                    System.out.println("Unknown exception occurs");
                    //todo handle
                }
            });
        });

        Platform.runLater(() -> {
            // todo fix updateFX files, as currently updating runs a temp copy of the new version, does not replace the old version
            //check for updates
            System.out.println("Checking for updates...");
            try {
                Path currentInstallPath = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                System.out.println(currentInstallPath);

                //todo determine if this is actually a .jar file. It should be, but not if running from debug.
                UpdateFX updater = new UpdateFX(Main.class,currentInstallPath);

                updater.checkUpdates();
            } catch (IOException ex) {
                System.out.println("Updating failed");
                ex.printStackTrace();
            }
            catch(Exception ex) {
                System.out.println("Unknown Exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Main(){
        icon16 = new Image(Main.class.getResource(IMG_ICO_PNG).toExternalForm(), 16,16,true,true);
        icon22 = new Image(Main.class.getResource(IMG_ICO_PNG).toExternalForm(), 22,22,true,true);
        icon24 = new Image(Main.class.getResource(IMG_ICO_PNG).toExternalForm(), 24,24,true,true);
        icon32 = new Image(Main.class.getResource(IMG_ICO_PNG).toExternalForm(), 32,32,true,true);
        icon48 = new Image(Main.class.getResource(IMG_ICO_PNG).toExternalForm(), 48,48,true,true);
    }
}
