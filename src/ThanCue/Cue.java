package ThanCue;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ryan on 15/05/16.
 */
public abstract class Cue implements Serializable{

    public enum CueEvents implements EventTypeInterface {
        INDEX_CHANGE, CUE_TYPE_CHANGE, CUE_NAME_CHANGE, CUE_BEHAVIOUR_CHANGE,
        CUE_PLAYDELAY_CHANGE, CUE_STARTPOINT_CHANGE, CUE_DURATION_CHANGE
    }





    private transient static final String IMG_SOUND_ICON = "/img/music.png";
    private transient static final String IMG_VIDEO_ICON = "/img/video.png";
    private transient static final String IMG_LIGHT_ICON = "/img/light.png";
    private transient static final String IMG_VOICE_ICON = "/img/speech.png";
    private transient static final String IMG_UNKNOWN_ICON = "/img/unknown.png";

    private transient SimpleIntegerProperty ind;
    private transient SimpleStringProperty cueType;
    private transient SimpleStringProperty cueName;
    private transient SimpleStringProperty cueBehaviour;
    protected transient SimpleStringProperty cueFilePath;
    private transient SimpleIntegerProperty cuePlayDelay;
    protected transient SimpleIntegerProperty cueStartPoint;
    protected transient SimpleIntegerProperty cueDuration;

    //fields that can't be a property (and thus are wrapped by one above)
    public CueType cueTypeEnum;
    public CueBehaviour cueBehaviourEnum;

    public Cue() {
        ind = new SimpleIntegerProperty(0);
        cueTypeEnum = CueType.UNKNOWN;
        cueType = new SimpleStringProperty(cueTypeEnum.toString());
        cueName = new SimpleStringProperty(Constants.defaultCueName);
        cueBehaviourEnum = CueBehaviour.PLAY_ON_GO;
        cueBehaviour = new SimpleStringProperty(cueBehaviourEnum.toString());
        cueFilePath = new SimpleStringProperty(Constants.filePathNotPresent);
        cuePlayDelay = new SimpleIntegerProperty(0);
        cueStartPoint = new SimpleIntegerProperty(0);
        cueDuration = new SimpleIntegerProperty(0);
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
        this.cueType.set(cueType.toString());
    }

    public String getCueBehaviour() {
        return cueBehaviour.get();
    }

    public abstract void stopCue();

    public void setCueBehaviour(CueBehaviour behaviour) {
        this.cueBehaviourEnum = behaviour;
        this.cueBehaviour.set(behaviour.toString());
    }

    public String getCueFilePath() {
        return cueFilePath.get();
    }

    public void setCueFilePath(Path path){
        System.out.println("Attempt to set filePath on non-file cue!");
    }

    public int getCuePlayDelay(){ return cuePlayDelay.get(); }

    public void setCuePlayDelay(int delay) { cuePlayDelay.set(delay); }

    public int getCueStartPoint() { return cueStartPoint.get(); }

    public void setCueStartPoint(int time) {
        System.out.println("Attempt to set startPoint on non-file cue!");
    }

    public int getCueDuration() { return cueDuration.get(); }

    public void setCueDuration(int time) {
        System.out.println("Attempt to set duration on non-file cue!");
    }

    @Override
    public String toString() {
        return "" + getIndex() + ". " + getCueType() + " - " + getCueName() + " - " + getCueBehaviour() + " - delay "  + getCuePlayDelay() + "ms - start " + getCueStartPoint() + "ms - duration " + getCueDuration() + "ms";
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        //todo update this every time we have something else to store/automatically do this for us
        oos.defaultWriteObject();
        //DO NOT, under any circumstances, change the order of these! It will break everything!!!
        List ser = new ArrayList();
        ser.add(getIndex());
        ser.add(getCueType());
        ser.add(getCueName());
        ser.add(getCueBehaviour());
        ser.add(getCueFilePath());
        ser.add(getCuePlayDelay());
        ser.add(getCueStartPoint());
        ser.add(getCueDuration());
        oos.writeObject(ser);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        //todo build in some backdated support for files
        ois.defaultReadObject();
        List ser = (List) ois.readObject();
        //todo check that length of list is correct
        //todo type validation (should be right, but worth checking

        //make sure these aren't null before calling a method on them (a source of pain otherwise)
        ind = new SimpleIntegerProperty();
        cueType = new SimpleStringProperty();
        cueName = new SimpleStringProperty();
        cueBehaviour = new SimpleStringProperty();
        cueFilePath = new SimpleStringProperty();
        cuePlayDelay = new SimpleIntegerProperty();
        cueStartPoint = new SimpleIntegerProperty();
        cueDuration = new SimpleIntegerProperty();
        System.out.println(ser.size());





        ind.set((Integer) ser.get(0));
        cueType.set((String) ser.get(1));
        cueName.set((String) ser.get(2));
        cueBehaviour.set((String) ser.get(3));
        cueFilePath.set((String) ser.get(4));
        cuePlayDelay.set((Integer) ser.get(5));
        cueStartPoint.set((Integer) ser.get(6));
        cueDuration.set((Integer) ser.get(7));
    }
    @Deprecated
    public String getFileString() {
        String fileString = "";
        String endField = String.valueOf((char) 31);

        fileString += cueType;
        fileString += endField;





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
            case "voice":
                return new ImageView(IMG_VOICE_ICON);
            default:
                return new ImageView(IMG_UNKNOWN_ICON);
        }
    }


}
