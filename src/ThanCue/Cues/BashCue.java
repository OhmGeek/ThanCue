package ThanCue.Cues;

import java.io.IOException;

/** This is essentially a custom cue. It allows one to run Bash commands through ThanCue
 *  It's not ideal, but we plan to extend this to make it more ideal with the GUI later on.
 * Created by ryan on 13/09/16.
 */
public class BashCue extends Cue {

    private transient Process instance;

    public BashCue() {
        super();
        this.setCueType(CueType.BASH);
    }

    @Override
    public void playCue() {
        //The command is stored in the name
        //this isn't ideal but we will adjust this later
        //todo GUI for BashCue
        //todo use ProcessBuilder instead with custom Bash Runner.
        try {
            String[] commands = getCueName().split(" ");
            instance = Runtime.getRuntime().exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopCue() {
        instance.destroy();
    }
}
