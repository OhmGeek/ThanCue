package mian;

/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {

    private String cueName;

    public Cue() {
        cueName = "Unnamed Cue";
    }

    public String getCueName() {
        return cueName;
    }

    public void setCueName(String name) {
        cueName = name;
    }

    @Override
    public String toString() {
        return getCueName();
    }

    public abstract void playCue(); //this plays the cue, be it lighting, sound, video or pushing over a table.


}
