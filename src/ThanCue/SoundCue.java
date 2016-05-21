package ThanCue;

import javax.print.DocFlavor;
import java.applet.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends Cue {

    private Path soundPath;

    public SoundCue() {
        setCueType("Sound");
    }

    public void setFilePath(String soundPath) {
        Path p = Paths.get(soundPath);
        this.soundPath = p;

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
            System.out.println("Error in playing song");
        }
    }

    @Override
    public String getFileString() {
        String current = super.getFileString();
        String endRecord = String.valueOf((char) 30);
        String endField = String.valueOf((char) 31);

        current += getFilePath().getFileName().toString(); //gets the name and extension.
        current += endField;
        current += endRecord;
        return current;
    }
}
