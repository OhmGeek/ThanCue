package ThanCue.Cues;

import ThanCue.VLC.VLCVideoPlayer;

/**
 * Created by mike on 08/06/16.
 */
public class VideoCue extends FileCue {
    private VLCVideoPlayer player = null;
    public VideoCue() {
        super();
        this.setCueType(CueType.VIDEO);
        this.setCueFilePath("https://youtu.be/dQw4w9WgXcQ");
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
