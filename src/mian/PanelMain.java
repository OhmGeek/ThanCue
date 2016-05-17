package mian;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 15/05/16.
 */
public class PanelMain {
    private JPanel pnlMain;
    private JList lstCues;
    private JButton btnNextCue;
    private List<Cue> cueCollection;
    public PanelMain() {
        cueCollection = new ArrayList<Cue>(); //this stores the latest cues. We can load this in from somewhere or create
                                            //new.
        populateCueCollection(); //this is a test.
        lstCues.setListData(cueCollection.toArray());
        
        btnNextCue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                playSelectedCue();
            }
        });
    }


    private void playSelectedCue() {
        Cue selectedCue = (Cue)lstCues.getSelectedValue();

        //if no cue selected, simply ignore it.
        if(selectedCue == null) {
            System.out.println("Selected Cue is null");
            return; //add some exceptions maybe. For now this is good enough.
        }
        selectedCue.playCue();
    }


    private void populateCueCollection() {
        Cue c1 = new SoundCue();
        Cue c2 = new SoundCue();
        c1.setCueName("Cue 1");
        c2.setCueName("Cue 2");
        cueCollection.add(c1);
        cueCollection.add(c2);
    }
    public JPanel getPanel() {
        return this.pnlMain;
    }
}
