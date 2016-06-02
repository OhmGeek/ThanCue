package ThanCue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    }
}
