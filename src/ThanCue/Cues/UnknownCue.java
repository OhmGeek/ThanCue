package ThanCue.Cues;

/**
 * Created by mike on 17/05/16.
 */
public class UnknownCue extends Cue {

    private static final long serialVersionUID = 100L;


    public UnknownCue() {
        this.setCueType(CueType.UNKNOWN);
    }

    @Override
    public void playCue() {
        System.out.println("Unknown cue has been played.");
    }
    public void stopCue() {
        System.out.println("Unknown cue has been stopped");
    }
}
