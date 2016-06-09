package ThanCue;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.nio.file.Path;


/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue {
    private static final String IMG_SOUND_ICON = "/img/music.png";
    private static final String IMG_VIDEO_ICON = "/img/video.png";
    private static final String IMG_LIGHT_ICON = "/img/light.png";
    private static final String IMG_UNKNOWN_ICON = "/img/unknown.png";

    private SimpleIntegerProperty ind;
    private SimpleStringProperty cueType;
    private SimpleStringProperty cueName;
    private SimpleStringProperty cueBehaviour;
    protected SimpleStringProperty cueFilePath;

    //fields that can't be a property (and thus are wrapped by one above)
    public CueType cueTypeEnum;
    public CueBehaviour cueBehaviourEnum;

    public Cue() {
        ind = new SimpleIntegerProperty(0);
        cueType = new SimpleStringProperty("Unset Type");
        cueName = new SimpleStringProperty("Unset Name");
        cueBehaviourEnum = CueBehaviour.PLAY_ON_GO;
        cueBehaviour = new SimpleStringProperty(cueBehaviourEnum.name().replace('_', ' ').toLowerCase());
        cueTypeEnum = CueType.UNSET;
        cueType = new SimpleStringProperty(cueTypeEnum.name().toLowerCase());
        cueFilePath = new SimpleStringProperty("n/a");
    }

    // NOTE: below, IntelliJ may suggest that these can be packageLocal. While true, this WILL break the tableview
    // so DO NOT UNDER ANY CIRCUMSTANCE make these packageLocal. It has caused many a headache already. :)

    public int getIndex() {
        return ind.get();
    }

    public void setIndex(Integer index) {
        ind.set(index);
    }

    public String getCueName() {
        return cueName.get();
    }

    public void setCueName(String name) {
        cueName.set(name);
    }

    public String getCueType() {
        return cueType.get();
    }

    public void setCueType(CueType cueType) {
        this.cueTypeEnum = cueType;
        this.cueType.set(cueType.name().toLowerCase());
    }

    public String getCueBehaviour() {
        return cueBehaviour.get();
    }

    public void setCueBehaviour(CueBehaviour behaviour) {
        this.cueBehaviourEnum = behaviour;
        this.cueBehaviour.set(behaviour.name().replace('_', ' ').toLowerCase());
    }

    public String getCueFilePath() {
        return "n/a";
    }

    public void setCueFilePath(Path path){
        System.out.println("Attempt to set filePath on non-file cue!");
    }

    @Override
    public String toString() {
        return "" + getIndex() + ". " + getCueType() + " - " + getCueName() + " - " + getCueBehaviour();
    }

    public String getFileString() {
        String fileString = "";
        String endField = String.valueOf((char) 31);

        //for each
        fileString += getCueName();
        fileString += endField;

        fileString += getCueType();
        fileString += endField;

        fileString += getCueBehaviour();
        fileString += endField;


        return fileString;
    }

    public void print() {
        System.out.println(toString());
    }

    public abstract void playCue(); //this plays the cue, be it lighting, sound, video or table flipping

    public static ImageView getImageView(String type) {
        switch (type.toLowerCase()) {
            case "sound":
                return new ImageView(IMG_SOUND_ICON);
            case "video":
                return new ImageView(IMG_VIDEO_ICON);
            case "light":
                return new ImageView(IMG_LIGHT_ICON);
            default:
                return new ImageView(IMG_UNKNOWN_ICON);
        }
    }


}
