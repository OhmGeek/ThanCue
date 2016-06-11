package ThanCue;

import ThanCue.VLC.VLCMusicPlayer;

/**
 * Created by ryan on 15/05/16.
 */
public class SoundCue extends FileCue {
    private VLCMusicPlayer player = null;
    public SoundCue() {
        super();
        this.setCueType(CueType.SOUND);
        this.setCueFilePath(System.getProperty("user.home") + "/testSound.wav");
    }

    @Override
    public void playCue() {
        player = new VLCMusicPlayer(soundPath.toAbsolutePath().toString());
        player.play();

    }
    @Override
    public void stopCue() { player.stop();
    }
}
