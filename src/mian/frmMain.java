package mian;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 15/05/16.
 */
public class frmMain {

    private JFrame frame;
    private JPanel pnlMain;
    private JList lstCues;
    private JButton btnNextCue, btnAddCue, btnMoveUp, btnMoveDown;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit;
    private JMenuItem menuItemFileNew, menuItemFileOpen, menuItemFileSave, menuItemFileSaveAs;
    private JCheckBoxMenuItem menuItemShowMode;

    private List<Cue> cueCollection;
    private Random r; // purely for adding test cues todo remove

    public frmMain(JFrame frame) {
        this.frame = frame;

        r = new Random();

        cueCollection = new ArrayList<>();
        lstCues.setListData(cues());

        registerActionListeners();
        createMenu();
        registerFileDrop();
    }

    private void registerFileDrop() {
        new FileDrop(this.getPanel(), files -> {
            if (cueCollection != null) {
                for (File f : files) {
                    //todo add checking to the type. Only allow for sound cues or videos.
                    SoundCue cToAdd = new SoundCue();
                    cToAdd.setCueName("New Dragged Cue - " + f.getAbsolutePath());
                    cToAdd.setFilePath(f.getAbsolutePath());
                    cueCollection.add(cToAdd);
                    lstCues.setListData(cues());
                }
            }
        });
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        menuItemFileNew = new JMenuItem("New", new ImageIcon());
        menuItemFileNew.setMnemonic(KeyEvent.VK_N);
        menuItemFileNew.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null, "New Cue dialog here", "Test", JOptionPane.INFORMATION_MESSAGE);
        });
        menuItemFileOpen = new JMenuItem("Open");
        menuItemFileOpen.setMnemonic(KeyEvent.VK_O);
        // todo action listeners from here down
        menuItemFileSave = new JMenuItem("Save");
        menuItemFileSave.setMnemonic(KeyEvent.VK_S);
        menuItemFileSaveAs = new JMenuItem("Save as");
        // todo what mnemonic for save as?
        menuItemShowMode = new JCheckBoxMenuItem("Show mode");
        menuItemShowMode.addActionListener(actionEvent -> {
            if (menuItemShowMode.isSelected()) {
                btnAddCue.setEnabled(false);
                btnMoveUp.setEnabled(false);
                btnMoveDown.setEnabled(false);
            } else {
                btnAddCue.setEnabled(true);
                btnMoveUp.setEnabled(true);
                btnMoveDown.setEnabled(true);
            }
        });

        menuBar.add(menuFile);
        menuFile.add(menuItemFileNew);
        menuFile.add(menuItemFileOpen);
        menuFile.add(menuItemFileSave);
        menuFile.add(menuItemFileSaveAs);
        menuFile.addSeparator();
        menuFile.add(menuItemShowMode);

        menuBar.add(menuEdit);

        frame.setJMenuBar(menuBar);
    }

    private void registerActionListeners() {
        btnAddCue.addActionListener(actionEvent -> {
            Cue c = new SoundCue();
            c.setCueName("Test cue " + r.nextInt());
            cueCollection.add(c);
            lstCues.setListData(cues());
        });

        btnMoveUp.addActionListener(actionEvent -> {
            if (lstCues.isSelectionEmpty())
                return;
            int toMove = lstCues.getSelectedIndex(); // todo support moving multiple indices at once
            if (toMove > 0) {
                Cue toSwapWith = cueCollection.get(toMove - 1);
                cueCollection.set(toMove - 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                lstCues.setListData(cues());
                lstCues.setSelectedIndex(toMove - 1);
            }
        });

        btnMoveDown.addActionListener(actionEvent -> {
            if (lstCues.isSelectionEmpty())
                return;
            int toMove = lstCues.getSelectedIndex(); // todo support moving multiple indices at once
            if (toMove < cueCollection.size() - 1) {
                Cue toSwapWith = cueCollection.get(toMove + 1);
                cueCollection.set(toMove + 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                lstCues.setListData(cues());
                lstCues.setSelectedIndex(toMove + 1);
            }
        });

        btnNextCue.addActionListener(actionEvent -> playSelectedCue());
    }

    //model stuff might be nicer because it's awesome. (Cue)(Object)(Cue)(Object)
    private Cue[] cues() { // todo if we want Object[], THEN we can just use cueCollection.toArray(); // todo look into models
        Object[] a = cueCollection.toArray();
        return Arrays.copyOf(a, a.length, Cue[].class);
    }

    private void playSelectedCue() {
        Cue selectedCue = (Cue) lstCues.getSelectedValue();

        //if no cue selected, simply ignore it.
        if (selectedCue == null) {
            System.out.println("Selected Cue is null");
            return; //add some exceptions maybe. For now this is good enough.
        }
        selectedCue.playCue();
    }



    JPanel getPanel() {
        return this.pnlMain;
    }
}
