package ThanCue;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends FileCue {

    public SoundCue() {
        super();
        this.setCueType(CueType.SOUND);
        this.setCueName("Mad sounds");
        this.setCueFilePath(System.getProperty("user.home") + "/testSound.wav");
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
}
