package ThanCue;

import sun.nio.cs.US_ASCII;

import javax.swing.*;


/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {
    private static final ImageIcon imgSoundIcon = new ImageIcon(Cue.class.getResource("/img/music.png"));
    private static final ImageIcon imgVideoIcon = new ImageIcon(Cue.class.getResource("/img/video.png"));
    private static final ImageIcon imgLightIcon = new ImageIcon(Cue.class.getResource("/img/light.png"));
    private static final ImageIcon imgUnknownIcon = new ImageIcon(Cue.class.getResource("/img/unknown.png"));


    private String cueType;
    private String cueName;
    private CueBehaviour behaviour;

    public Cue() {
        cueType = "Unset Cue";
        cueName = "Unset Cue";
        behaviour = CueBehaviour.PLAY_ON_GO;
    }

    String getCueName() {
        return cueName;
    }

    String getCueType() {
        return cueType;
    }

    ImageIcon getIcon() {
        return Cue.getIcon(cueType);
    }

    CueBehaviour getBehaviour() {
        return behaviour;
    }

    void setCueType(String cueType) {
        this.cueType = cueType;
    }

    void setCueName(String name) {
        cueName = name;
    }

    void setBehaviour(CueBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    @Override
    public String toString() {
        return getCueType() + " - " + getCueName();
    }

    public String getFileString() {
        String fileString = "";
        String endField = String.valueOf((char) 31);




        //for each
        fileString += getCueName();
        fileString += endField;

        fileString += getCueType();
        fileString += endField;

        fileString += behaviour;
        fileString += endField;

        return fileString;
    }

    public void print(){
        System.out.println(cueType + " - " + cueName + " - " + behaviour.name().toLowerCase().replace("_", " "));
    }

    public Object[] getAttributeArray(){
        return new Object[]{0, Cue.getIcon(cueType), cueType, cueName, behaviour.name().toLowerCase().replace("_", " ")};
    }

    public abstract void playCue(); //this plays the cue, be it lighting, sound, video or table flipping

    private static ImageIcon getIcon(String type) {
        /*

        Note: required to do this way, else ALL table entries get the icon of the most recently added cue

         */
        switch (type.toLowerCase()) {
            case "sound":
                return imgSoundIcon;
            case "video":
                return imgVideoIcon;
            case "light":
                return imgLightIcon;
            default:
                return imgUnknownIcon;
        }
    }



}
