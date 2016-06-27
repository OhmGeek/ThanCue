package ThanCue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ThanCue.Constants.endFieldCharacter;
import static ThanCue.Constants.endRecordCharacter;

/**
 * Created by mike on 08/06/16.
 */
public abstract class FileCue extends Cue {

    protected transient Path soundPath;

    public FileCue(){
        super();
        this.setCueFilePath(System.getProperty("user.home") + "/myCue.cue");
    }

    @Override
    public void setCueStartPoint(int time) { cueStartPoint.set(time); }
    @Override
    public void setCueDuration(int time) { cueDuration.set(time); }

    public Path getFilePath(){
        return this.soundPath;
    }

    @Override
    public String getCueFilePath() { //for table
        return getFilePath().toAbsolutePath().toString();
    }

    public void setCueFilePath(String soundPath) {
        Path p = Paths.get(soundPath);
        setCueFilePath(p);
    }

    @Override
    public void setCueFilePath(Path path){ //for table
        this.soundPath = path;
        cueFilePath.set(path.toAbsolutePath().toString());
    }

    @Override
    public String getFileString() {
        String current = super.getFileString();

        current = "sc" + endFieldCharacter + current; //marks file as a sound cue.
        current += getFilePath().getFileName().toString(); //gets the name and extension.
        current += endFieldCharacter;
        current += endRecordCharacter;
        return current;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        //todo update this every time we have something else to store/automatically do this for us
        oos.defaultWriteObject();
        oos.writeObject(getFilePath().getFileName().toString());
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        //todo build in some backdated support for files
        ois.defaultReadObject();
        setCueFilePath(Paths.get((String) ois.readObject()));

    }

}
