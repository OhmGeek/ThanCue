package ThanCue;

/**
 * Created by mike on 27/06/16.
 */
public class VoiceCue extends Cue {

    Process voice;

    public VoiceCue(){
        this.setCueType(CueType.VOICE);
    }

    @Override
    public void playCue() {
        try {
            voice = Runtime.getRuntime().exec(new String[]{"espeak", getCueName()}); //todo mac/windows support like with vlcmusicplayer
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void stopCue() {
        voice.destroy();
        // todo not sure stopping works like this, just guessing
    }
}
