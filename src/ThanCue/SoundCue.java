package ThanCue;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends Cue {

    private Path soundPath;

    public SoundCue() {
        this.setFilePath(System.getProperty("user.home") + "/my_cue.cue");
        this.setCueType(CueType.SOUND);
        this.setCueName("Mad sounds");
    }

    public void setFilePath(String soundPath) {
        Path p = Paths.get(soundPath);
        this.soundPath = p;
        //this.setCueName(soundPath); // user may want custom name other than file path
    }

    public Path getFilePath() {
        return this.soundPath;
    }

    @Override
    public void playCue() {
        //todo use vlc
	    try {
            URL u;
            u = soundPath.toUri().toURL();
            System.out.println(u.toString());
            AudioClip sound = Applet.newAudioClip(u);
            sound.play(); //one can also loop this
        } catch(Exception ex) {
            System.out.println("Error in playing sound cue");
        }
    }

    @Override
    public String getFileString() {
        String current = super.getFileString();

        //todo put all constants in a separate class (called Constants). Perhaps a FileConstants one?
        String endRecord = String.valueOf((char) 30);
        String endField = String.valueOf((char) 31);


        current = "sc" + endField + current; //marks file as a sound cue.
        current += getFilePath().getFileName().toString(); //gets the name and extension.
        current += endField;
        current += endRecord;
        return current;
    }
}
