package mian;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends Cue {

    private Path soundPath;

    public SoundCue() {

    }
    public void setFilePath(Path soundPath) {
        this.soundPath = soundPath;
    }
    public Path getFilePath() {
        return this.soundPath;
    }
    public void playCue() {
        JOptionPane.showMessageDialog(null,"Playing Cue", "Test",JOptionPane.INFORMATION_MESSAGE);
    }
}
