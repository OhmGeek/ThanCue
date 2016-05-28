package ThanCue;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ThanCue: " + Constants.VERSION_NAME);
        primaryStage.setScene(new Scene(root,300,275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
