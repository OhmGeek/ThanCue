package mian;

import javax.swing.*;

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

        //now create the default view and display it.
        PanelMain mainPanel = new PanelMain();
        setContentDisplay(mainPanel.getPanel());

    }

    private void setContentDisplay(JPanel p) {
        window.setContentPane(p);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    private void setWindowTitle(String title) {
        window.setTitle(title);
    }


}
