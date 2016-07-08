package ThanCue.Cues;

import ThanCue.VLC.VLCVideoPlayer;

/**
 * Created by mike on 08/06/16.
 */
public class VideoCue extends FileCue {

    private transient VLCVideoPlayer player = null;
    private static final long serialVersionUID = 10102L;

    public VideoCue() {
        super();
        this.setCueType(CueType.VIDEO);
        this.setCueFilePath("a");
    }

    @Override
    public void playCue() {
        player = new VLCVideoPlayer(soundPath.toAbsolutePath().toString());
        player.play();
    }

    @Override
    public void stopCue() {
        player.stop();
    }
}
