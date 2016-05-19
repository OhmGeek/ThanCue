package ThanCue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton btnNextCue, btnAddCue, btnMoveUp, btnMoveDown;
    private JScrollPane scrCueListScroll;
    private JTable lstCuesT;
    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit;
    private JMenuItem menuItemFileNew, menuItemFileOpen, menuItemFileSave, menuItemFileSaveAs;
    private JCheckBoxMenuItem menuItemShowMode;

    private List<Cue> cueCollection;
    private Random r; // purely for adding test cues todo remove

    public frmMain(JFrame frame) {
        //set the UI to match the OS default feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //set the jframe
        this.frame = frame;

        r = new Random();

        //create the cue collection and set the list data
        cueCollection = new ArrayList<>();

        //deal with menus, file dropping, and list rendering.
        updateTable();
        tableSetupOneTimeRun();
        registerActionListeners();
        createMenu();
        registerFileDrop();
    }

    private void tableSetupOneTimeRun() {
        lstCuesT.setColumnSelectionAllowed(false);
        lstCuesT.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            if(lstCuesT.getSelectedRows().length > 0){
                btnNextCue.setEnabled(true);
            }else{
                btnNextCue.setEnabled(false);
            }
        });
    }

    private final String[] columnNames = {"Icon", "Type", "Name", "Behaviour"}; //outside to avoid destroying and remaking EVERY UPDATE
    private void updateTable() {
        Object[][] cuesAtts = new Object[cueCollection.size()][];
        for (int i = 0; i < cueCollection.size(); i++) {
            cuesAtts[i] = cueCollection.get(i).getAttributeArray();
        }
        lstCuesT.setModel(new DefaultTableModel(cuesAtts, columnNames) {
            Class[] types = new Class[]{
                    ImageIcon.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean[]{false, false, false, false}; //one for each column

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void registerFileDrop() {
        new FileDrop(this.getPanel(), files -> {
            if (cueCollection != null) {
                for (File f : files) {
                    //todo add checking to the type. Only allow for sound cues or videos.
                    SoundCue cToAdd = new SoundCue();
                    cToAdd.setCueName(f.getAbsolutePath());
                    cToAdd.setFilePath(f.getAbsolutePath());
                    cueCollection.add(cToAdd);
                }
                updateTable();
            }
        });
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        menuItemFileNew = new JMenuItem("New");
        menuItemFileNew.setMnemonic(KeyEvent.VK_N);
        menuItemFileNew.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null, "New Cue dialog here", "Test", JOptionPane.INFORMATION_MESSAGE);
        });
        menuItemFileOpen = new JMenuItem("Open");
        menuItemFileOpen.setMnemonic(KeyEvent.VK_O);
        // todo action listeners from here down
        menuItemFileSave = new JMenuItem("Save", new ImageIcon("img/save.png"));
        menuItemFileSave.setMnemonic(KeyEvent.VK_S);
        menuItemFileSaveAs = new JMenuItem("Save as");
        // todo what mnemonic for save as?
        menuItemShowMode = new JCheckBoxMenuItem("Show mode");
        menuItemShowMode.addActionListener(actionEvent -> {
            if (menuItemShowMode.getState()) {
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
            Cue c = new UnknownCue();
            c.setCueName("Test cue " + r.nextInt(1000));
            cueCollection.add(c);
            if(lstCuesT.getSelectedRows().length != 0) {
                int sel = lstCuesT.getSelectedRow();
                updateTable();
                lstCuesT.setRowSelectionInterval(sel, sel);
            }else{
                updateTable();
            }
        });

        btnMoveUp.addActionListener(actionEvent -> {
            if (lstCuesT.getSelectedRows().length == 0)
                return;
            int toMove = lstCuesT.getSelectedRow(); // todo support moving multiple indices at once
            if (toMove > 0) {
                Cue toSwapWith = cueCollection.get(toMove - 1);
                cueCollection.set(toMove - 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                updateTable();
                lstCuesT.setRowSelectionInterval(toMove - 1, toMove - 1);
            }
        });

        btnMoveDown.addActionListener(actionEvent -> {
            if (lstCuesT.getSelectedRows().length == 0)
                return;
            int toMove = lstCuesT.getSelectedRow(); // todo support moving multiple indices at once
            if (toMove < cueCollection.size() - 1) {
                Cue toSwapWith = cueCollection.get(toMove + 1);
                cueCollection.set(toMove + 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                updateTable();
                lstCuesT.setRowSelectionInterval(toMove + 1, toMove + 1);
            }
        });

        btnNextCue.addActionListener(actionEvent -> playSelectedCue());

        // note: lstCues (now lstCuesT) selection changed listener migrated to method: tableSetupOneTimeRun()
    }

    //model stuff might be nicer because it's awesome. (Cue)(Object)(Cue)(Object)
    private Cue[] cues() { // todo if we want Object[], THEN we can just use cueCollection.toArray(); // todo look into models
        Object[] a = cueCollection.toArray();
        return Arrays.copyOf(a, a.length, Cue[].class);
    }

    private void playSelectedCue() {
        if(lstCuesT.getSelectedRows().length == 0){
            return;
        }
        Cue selectedCue = cueCollection.get(lstCuesT.getSelectedRow());

        //if no cue selected, simply ignore it.
        if (selectedCue == null) {
            System.out.println("Selected Cue is null");
            return; //add some exceptions maybe. For now this is good enough.
        }
        selectedCue.playCue();

        //advance selection
        int sel = lstCuesT.getSelectedRow() + 1;
        if (sel == cueCollection.size()) {
            if (menuItemShowMode.getState()) {
                //DO NOT LOOP AROUND in show mode
                lstCuesT.clearSelection();
            } else {
                //do loop around out of show mode
                lstCuesT.setRowSelectionInterval(0, 0);
            }
        } else {
            //not at the end, ie. not ready to try and loop yet
            lstCuesT.setRowSelectionInterval(sel, sel);
        }
    }


    JPanel getPanel() {
        return this.pnlMain;
    }

}
