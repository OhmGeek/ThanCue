package ThanCue;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 15/05/16.
 */
public class frmMain {

    private JFrame frame;
    private JPanel pnlMain;
    private JButton btnPlay, btnAdd, btnMoveUp, btnMoveDown;
    private JScrollPane scrTableScroller;
    private JTable tblCueView;
    private JButton btnEditCue;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        //set the jframe
        this.frame = frame;

        r = new Random();

        //create the cue collection and set the list data
        cueCollection = new ArrayList<>();

        //deal with menus, file dropping, and list rendering.
        updateTable();
        tableSetupOneTimeRun();
        buttonSetupOneTimeRun();
        menuSetupOneTimeRun();
        registerFileDrop();
    }

    private static final String[] columnNames = {"#", "Icon", "Type", "Name", "Behaviour"}; //outside to avoid destroying and remaking EVERY UPDATE

    private void updateTable() {
        Object[][] cuesAtts = new Object[cueCollection.size()][];
        for (int i = 0; i < cueCollection.size(); i++) {
            cueCollection.get(i).setInd(i);
            cuesAtts[i] = cueCollection.get(i).getAttributeArray();
        }
        TableModel model = new DefaultTableModel(cuesAtts, frmMain.columnNames) {
            Class[] types = new Class[]{
                    Integer.class, ImageIcon.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean[]{false, false, false, true, false}; //one for each column

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        model.addTableModelListener(tableModelEvent -> {
            int row = tableModelEvent.getFirstRow();
            int column = tableModelEvent.getColumn();
            Object newVal = tblCueView.getValueAt(row, column);
            System.out.println("\nColumn " + column + " Row " + row + " manually changed to " + newVal.toString());

            Cue c = cueCollection.get(row);
            switch (tblCueView.getColumnName(column)) {
                case "Name":
                    c.setCueName(newVal.toString());
                    break;
                default:
                    System.out.println("Unexpected column " + tblCueView.getColumnName(column) + " was changed!");
                    break;
            }
            cueCollection.set(row, c);
            System.out.println("List has been updated to reflect change");
        });
        tblCueView.setModel(model);
    }

    private static final ArrayList<String> soundExtensions = new ArrayList<String>() {{
        add("mp3");
        add("wav");
        add("ogg");

    }};

    public static void crashComputer() {
        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        crashComputer();
                    }
                }
            });
            thread.start();
        }
    }

    public static void crashJVM() {
        while(true)
            crashJVM();
    }

    private void registerFileDrop() {
        new FileDrop(this.getPanel(), files -> {
            if (cueCollection != null) {
                for (File f : files) {
                    String[] nameParts = f.getName().split(".");
                    String extension = nameParts[nameParts.length - 1];
                    //todo BETTER add checking to the type. Only allow for sound cues or videos.
                    if (soundExtensions.contains(extension)) {
                        SoundCue cToAdd = new SoundCue();
                        cToAdd.setCueName(f.getName());
                        cToAdd.setFilePath(f.getAbsolutePath());
                        cueCollection.add(cToAdd);
                    } else {

                    }
                }
                updateTable();
            }
        });
    }

    private void menuSetupOneTimeRun() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        /*
        DEV MENU
         */
        JMenu dev = new JMenu("Dev");
        dev.setMnemonic(KeyEvent.VK_D);
        menuBar.add(dev);
        JMenuItem printCues = new JMenuItem("Print all cues");
        printCues.setMnemonic(KeyEvent.VK_P);
        printCues.addActionListener(actionEvent -> {
            System.out.println("\nCues:");
            for (Cue c : cueCollection) {
                c.print();
            }
        });
        dev.add(printCues);


        JMenuItem fun = new JMenuItem("I'm a clicker");
        fun.setMnemonic(KeyEvent.VK_Q);
        fun.addActionListener(actionEvent -> {
            crashComputer();
        });
        dev.add(fun);
        /*
        END OF DEV MENU
         */

        menuItemFileNew = new JMenuItem("New");
        menuItemFileNew.setMnemonic(KeyEvent.VK_N);
        menuItemFileNew.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null, "New Cue dialog here", "Test", JOptionPane.INFORMATION_MESSAGE);
        });
        menuItemFileOpen = new JMenuItem("Open", new ImageIcon(getClass().getResource("/img/open.png")));
        menuItemFileOpen.setMnemonic(KeyEvent.VK_O);
        menuItemFileOpen.addActionListener(actionEvent -> {

            CueFileManager man = new CueFileManager();
            try {
                JFileChooser filePicker = new JFileChooser();
                filePicker.setDialogTitle("Specify file to load");
                int userSelection = filePicker.showOpenDialog(this.frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = filePicker.getSelectedFile();
                    if (fileToOpen.exists()) {
                        //todo check that the old cueList has been saved and we aren't overwriting anything
                        List<Cue> cueList = man.readCue(fileToOpen);
                        System.out.println(cueList.toString());
                        System.out.println("Length " + cueList.size());
                        this.cueCollection = cueList;
                        updateTable();

                    } else
                        throw new FileNotFoundException();
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        });
        // todo all action listeners from here down
        menuItemFileSave = new JMenuItem("Save", new ImageIcon(getClass().getResource("/img/save.png")));
        menuItemFileSave.setMnemonic(KeyEvent.VK_S);
        menuItemFileSave.addActionListener(actionEvent -> {
            CueFileManager man = new CueFileManager();
            try {
                JFileChooser filePicker = new JFileChooser();
                filePicker.setDialogTitle("Specify destination");
                int userSelection = filePicker.showSaveDialog(this.frame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = filePicker.getSelectedFile();
                    man.writeCue(fileToSave.getParent() + "/", fileToSave.getName(), cueCollection);
                    System.out.println(fileToSave.getParent() + fileToSave.getName());
                }

            } catch (Exception ex) {
                System.out.println("Error!");
                ex.printStackTrace();
            }
        });

        menuItemFileSaveAs = new JMenuItem("Save as", new ImageIcon(getClass().getResource("/img/save.png")));

        // todo what mnemonic for save as?
        menuItemShowMode = new JCheckBoxMenuItem("Show mode");
        menuItemShowMode.addActionListener(actionEvent -> {
            if (menuItemShowMode.getState()) {
                btnAdd.setEnabled(false);
                btnEditCue.setEnabled(false);
                btnMoveUp.setEnabled(false);
                btnMoveDown.setEnabled(false);
            } else {
                btnAdd.setEnabled(true);
                btnEditCue.setEnabled(true);
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

    private void buttonSetupOneTimeRun() {
        btnAdd.addActionListener(actionEvent -> {
            Cue c = new UnknownCue();
            c.setCueName("Test cue " + r.nextInt(1000));
            cueCollection.add(c);
            if (tblCueView.getSelectedRows().length != 0) {
                int sel = tblCueView.getSelectedRow();
                updateTable();
                tblCueView.setRowSelectionInterval(sel, sel);
            } else {
                updateTable();
            }
        });
        btnAdd.setFont(new Font("Arial", Font.PLAIN, 25));

        btnEditCue.addActionListener(actionEvent -> {
            // todo new form pop up here for editing the cue yo
        });
        btnEditCue.setFont(new Font("Arial", Font.PLAIN, 25));

        btnMoveUp.addActionListener(actionEvent -> {
            if (tblCueView.getSelectedRows().length == 0)
                return;
            int toMove = tblCueView.getSelectedRow(); // todo support moving multiple indices at once
            if (toMove > 0) {
                Cue toSwapWith = cueCollection.get(toMove - 1);
                cueCollection.set(toMove - 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                updateTable();
                tblCueView.setRowSelectionInterval(toMove - 1, toMove - 1);
            }
        });
        btnMoveUp.setFont(new Font("Arial", Font.PLAIN, 25));

        btnMoveDown.addActionListener(actionEvent -> {
            if (tblCueView.getSelectedRows().length == 0)
                return;
            int toMove = tblCueView.getSelectedRow(); // todo support moving multiple indices at once
            if (toMove < cueCollection.size() - 1) {
                Cue toSwapWith = cueCollection.get(toMove + 1);
                cueCollection.set(toMove + 1, cueCollection.get(toMove));
                cueCollection.set(toMove, toSwapWith);
                updateTable();
                tblCueView.setRowSelectionInterval(toMove + 1, toMove + 1);
            }
        });
        btnMoveDown.setFont(new Font("Arial", Font.PLAIN, 25));

        btnPlay.addActionListener(actionEvent -> playSelectedCue());
        btnPlay.setFont(new Font("Arial", Font.PLAIN, 40));

        // note: lstCues (now tblCueView) selection changed listener migrated to method: tableSetupOneTimeRun()
    }

    private void tableSetupOneTimeRun() {
        tblCueView.setFont(new Font("Arial", Font.PLAIN, 14));
        tblCueView.setColumnSelectionAllowed(false);
        tblCueView.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            if (tblCueView.getSelectedRows().length > 0) {
                btnPlay.setEnabled(true);
            } else {
                btnPlay.setEnabled(false);
            }
        });
    }

    private void playSelectedCue() {
        if (tblCueView.getSelectedRows().length == 0) {
            return;
        }
        Cue selectedCue = cueCollection.get(tblCueView.getSelectedRow());
        int selCueIndex = tblCueView.getSelectedRow();

        //if no cue selected, simply ignore it.
        if (selectedCue == null) {
            System.out.println("Selected Cue is null");
            return; //add some exceptions maybe. For now this is good enough.
        }
        selectedCue.playCue();

        //play WITH previous todo untested, so test
        while (cueCollection.size() > selCueIndex + 1 && cueCollection.get(selCueIndex + 1).getBehaviour() == CueBehaviour.PLAY_WITH_PREVIOUS) {
            selCueIndex++;
            selectedCue = cueCollection.get(selCueIndex);
            selectedCue.playCue();
        }


        //advance selection
        int sel = selCueIndex + 1;
        if (sel == cueCollection.size()) {
            if (menuItemShowMode.getState()) {
                //DO NOT LOOP AROUND in show mode
                tblCueView.clearSelection();
            } else {
                //do loop around out of show mode
                tblCueView.setRowSelectionInterval(0, 0);
            }
        } else {
            //not at the end, ie. not ready to try and loop yet
            tblCueView.setRowSelectionInterval(sel, sel);
        }
    }

    JPanel getPanel() {
        return this.pnlMain;
    }

}
