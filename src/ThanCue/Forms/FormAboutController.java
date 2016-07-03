package ThanCue.Forms;

import ThanCue.Variables.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Created by mike on 11/06/16.
 */
public class FormAboutController {
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private VBox vbox;

    @FXML
    private Label lblAboutInfo;
    @FXML
    private Button btnClose;
    @FXML
    private Hyperlink btnThanCue;
    @FXML
    private Hyperlink btnMike;
    @FXML
    private Hyperlink btnRyan;

    @FXML
    public void initialize() {
        setText();
        setActions();
        setSizes();
    }

    private void setText() {
        lblAboutInfo.setText(Constants.ABOUT_TEXT);
        btnThanCue.setText(btnThanCue.getText() + Constants.URL_GITHUB_THANCUE);
        btnMike.setText(btnMike.getText() + Constants.URL_GITHUB_MIKE);
        btnRyan.setText(btnRyan.getText() + Constants.URL_GITHUB_RYAN);
    }

    private void setSizes() {
        AnchorPane.setTopAnchor(vbox, 14.0);
        AnchorPane.setBottomAnchor(vbox, 14.0);
        AnchorPane.setLeftAnchor(vbox, 14.0);
        AnchorPane.setRightAnchor(vbox, 14.0);

        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lblAboutInfo.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        lblAboutInfo.setAlignment(Pos.CENTER);
        lblAboutInfo.wrapTextProperty().setValue(true);
        lblAboutInfo.textAlignmentProperty().setValue(TextAlignment.CENTER);

        // todo fix this error:
        //btnClose.getScene().getWindow().sizeToScene();
    }

    private void setActions() {
        btnThanCue.setOnAction(event -> openWebsite(Constants.URL_GITHUB_THANCUE));
        btnMike.setOnAction(event -> openWebsite(Constants.URL_GITHUB_MIKE));
        btnRyan.setOnAction(event -> openWebsite(Constants.URL_GITHUB_RYAN));

        btnClose.setOnAction(event -> close());
    }

    private void openWebsite(String site) {
        //windows or mac:
        //java.awt.Desktop.getDesktop().browse(url.toURI());

        try {
            if (Runtime.getRuntime().exec(new String[]{"which", "xdg-open"}).getInputStream().read() != -1) {
                Runtime.getRuntime().exec(new String[]{"xdg-open", site});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void close() {
        Stage thisStage = (Stage) btnClose.getScene().getWindow();
        thisStage.close();
    }
}
