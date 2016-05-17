package ThanCue;

import java.applet.*;
import java.net.*;
import java.nio.file.Paths;

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
        //todo use vlc
	    try {
            URL u;
            u = Paths.get(soundPath).toUri().toURL();
            System.out.println(u.toString());
            AudioClip sound = Applet.newAudioClip(u);
            sound.play(); //one can also loop this
        } catch(Exception ex) {
            System.out.println("Error in playing song");
        }
    }
}
