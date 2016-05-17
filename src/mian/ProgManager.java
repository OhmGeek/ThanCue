package mian;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 17/05/16.
 *
 */
public class ProgManager {
    private JFrame window;
    private static final String PROG_NAME = "ThanCue";

    public ProgManager() {

        window = new JFrame();
        setWindowTitle(PROG_NAME);


    }
    public void go() {
        //now create the default view and display it.
        PanelMain mainPanel = new PanelMain();
        setContentDisplay(mainPanel.getPanel());

    }
    public void setContentDisplay(JPanel p) {
        window.setContentPane(p);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }


    public void setWindowTitle(String title) {
        window.setTitle(title);
    }


}
