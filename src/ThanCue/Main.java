package ThanCue;

import com.briksoftware.updatefx.core.UpdateFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FormMain.fxml"));
        primaryStage.setTitle("ThanCue " + Constants.VERSION_NAME + " (" + Constants.RELEASE_ID + ")");
        primaryStage.setScene(new Scene(root,600,550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        //check for updates
        try {
            UpdateFX updater = new UpdateFX(Main.class);
            updater.checkUpdates();
        }catch(IOException ex){
            System.out.println("Updating failed");
            ex.printStackTrace();
        }
    }
}
