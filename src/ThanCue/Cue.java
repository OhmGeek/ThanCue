package ThanCue;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.swing.*;


/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {
    private static final ImageIcon imgSoundIcon = new ImageIcon(Cue.class.getResource("/img/music.png"));
    private static final ImageIcon imgVideoIcon = new ImageIcon(Cue.class.getResource("/img/video.png"));
    private static final ImageIcon imgLightIcon = new ImageIcon(Cue.class.getResource("/img/light.png"));
    private static final ImageIcon imgUnknownIcon = new ImageIcon(Cue.class.getResource("/img/unknown.png"));

    private SimpleIntegerProperty index;
    private SimpleStringProperty cueType;
    private String cueName;
    private CueBehaviour behaviour;

    public Cue() {
        index = new SimpleIntegerProperty(-1);
        cueType = new SimpleStringProperty("Unset Cue");
        cueName = "Unset Cue";
        behaviour = CueBehaviour.PLAY_ON_GO;
    }

    private int getIndex() {
        return index.get();
    }

    public void setIndex(int index) { this.index.set(index); }

    public String getCueName() {
        return cueName;
    }

    public String getCueType() {
        return cueType.get();
    }

    public ImageIcon getIcon() {
        return Cue.getIcon(getCueType());
    }

    public CueBehaviour getBehaviour() {
        return behaviour;
    }

    public void setCueType(String cueType) {
        this.cueType.set(cueType);
    }

    public void setCueName(String name) {
        cueName = name;
    }

    public void setBehaviour(CueBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    @Override
    public String toString() {
        return "" + getIndex() + ". " + getCueType() + " - " + getCueName() + " - " + getBehaviour().name().toLowerCase().replace("_", " ");
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

    public void print() {
        System.out.println(toString());
    }

    public Object[] getAttributeArray() {
        return new Object[]{getIndex(), Cue.getIcon(getCueType()), getCueType(), getCueName(), behaviour.name().toLowerCase().replace("_", " ")};
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
