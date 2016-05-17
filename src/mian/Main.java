package mian;

import javax.swing.*;

/**
 * Created by ryan on 15/05/16.
 */




public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("ThanCue");
        PanelMain mainPanel = new PanelMain();

        window.setContentPane(mainPanel.getPanel());
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}
