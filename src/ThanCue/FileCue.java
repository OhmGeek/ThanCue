package ThanCue;

import java.nio.file.Path;
import java.nio.file.Paths;

import static ThanCue.Constants.endField;
import static ThanCue.Constants.endRecord;

/**
 * Created by mike on 08/06/16.
 */
public abstract class FileCue extends Cue {

    protected Path soundPath;

    public FileCue(){
        super();
        this.setCueFilePath(System.getProperty("user.home") + "/my_cue.cue");
    }

    public void setCueFilePath(String soundPath) {
        Path p = Paths.get(soundPath);
        this.soundPath = p;
        cueFilePath.set(soundPath);
        //this.setCueName(soundPath); // user may want custom name other than file path
    }

    public Path getFilePath(){
        return this.soundPath;
    }

    @Override
    public String getCueFilePath() { //for table
        return this.soundPath.toAbsolutePath().toString();
    }

    @Override
    public void setCueFilePath(Path path){ //for table
        this.soundPath = path;
        cueFilePath.set(path.toAbsolutePath().toString());
    }

    @Override
    public String getFileString() {
        String current = super.getFileString();

        current = "sc" + endField + current; //marks file as a sound cue.
        current += getFilePath().getFileName().toString(); //gets the name and extension.
        current += endField;
        current += endRecord;
        return current;
    }
}
