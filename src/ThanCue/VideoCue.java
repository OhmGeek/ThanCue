package ThanCue;

/**
 * Created by mike on 08/06/16.
 */
public class VideoCue extends FileCue {

    public VideoCue() {
        super();
        this.setCueType(CueType.VIDEO);
        this.setCueName("Video time");
    }

    @Override
    public void playCue() {

    }
}
