package ThanCue.Cues;

import ThanCue.VLC.VLCMusicPlayer;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends FileCue {

    private transient VLCMusicPlayer player = null;

    private long serialVersionUID = 10101L;


    public SoundCue() {
        super();
        this.setCueType(CueType.SOUND);
        //this.setCueFilePath("https://youtu.be/dQw4w9WgXcQ");
    }

    @Override
    public void playCue() {
        player = new VLCMusicPlayer(soundPath.toAbsolutePath().toString(), getCueStartPoint(), getCueDuration());
        player.play();

    }
    @Override
    public void stopCue() { player.stop(); }


}
