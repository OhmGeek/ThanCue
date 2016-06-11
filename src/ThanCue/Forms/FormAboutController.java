package ThanCue.Forms;

import ThanCue.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    public void initialize() {
        setText();
        setActions();
        setSizes();
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

        // todo remove the extra space at the bottom of the form
    }

    private void setActions() {
        btnClose.setOnAction(event -> close());
    }

    private void close() {
        Stage thisStage = (Stage)btnClose.getScene().getWindow();
        thisStage.close();
    }

    private void setText() {
        lblAboutInfo.setText(Constants.aboutText);
    }
}
