package ThanCue;

import ThanCue.VLC.VLCVideoPlayer;

/**
 * Created by mike on 08/06/16.
 */
public class VideoCue extends FileCue {
    private VLCVideoPlayer player = null;
    public VideoCue() {
        super();
        this.setCueType(CueType.VIDEO);
        this.setCueFilePath(System.getProperty("user.home") + "/testVideo.mp4");
    }

    @Override
    public void playCue() {
        player = new VLCVideoPlayer(soundPath.toAbsolutePath().toString());
        player.play();
    }
    public void stopCue() {
        player.stop();
    }
}
