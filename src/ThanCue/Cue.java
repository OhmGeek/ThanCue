package ThanCue;

import javax.swing.*;

/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {

    private String cueType;
    private String cueName;
    private static ImageIcon icon;

    public Cue() {
        cueType = "Unset Cue";
        cueName = "Unset Cue";
        icon = new ImageIcon("img/unknown.png");
    }

    String getCueName() {
        return cueName;
    }

    void setCueType(String cueType) {
        this.cueType = cueType;
    }

    String getCueType() {
        return cueType;
    }

    void setCueName(String name) {
        cueName = name;
    }

    ImageIcon getIcon(){
        return icon;
    }

    void setIcon(String path){
        icon = new ImageIcon(path);
    }

    @Override
    public String toString() {
        return getCueType() + " - " + getCueName();
    }

    public abstract void playCue(); //this plays the cue, be it lighting, sound, video or pushing over a table.


}
