package mian;

/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {

    private String cueType;
    private String cueName;

    public Cue() {
        cueType = "Untyped";
        cueName = "Unnamed Cue";
    }

    private String getCueName() {
        return cueName;
    }

    void setCueType(String cueType) {
        this.cueType = cueType;
    }

    private String getCueType() {
        return cueType;
    }

    void setCueName(String name) {
        cueName = name;
    }

    @Override
    public String toString() {
        return getCueType() + " - " + getCueName();
    }

    public abstract void playCue(); //this plays the cue, be it lighting, sound, video or pushing over a table.


}
