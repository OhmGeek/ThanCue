package mian;

import javax.swing.*;

/**
 * Created by ryan on 15/05/16.
 */




public class Main {
    public static void main(String[] args) {
        //this is the main class to call.
        PanelMain panel = new PanelMain();
        JFrame frame = new JFrame("ThanCue (for the music)");
        frame.setContentPane(panel.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
