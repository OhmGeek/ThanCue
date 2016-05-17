package mian;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends Cue {

    private String soundPath;

    public SoundCue() {
        setCueType("Sound");
    }

    public void setFilePath(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getFilePath() {
        return this.soundPath;
    }

    public void playCue() {
	JOptionPane.showMessageDialog(null,"Playing Cue", "Test",JOptionPane.INFORMATION_MESSAGE);
    }
}
