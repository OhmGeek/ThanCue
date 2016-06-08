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
        this.setFilePath(System.getProperty("user.home") + "/my_cue.cue");
    }

    public void setFilePath(String soundPath) {
        Path p = Paths.get(soundPath);
        this.soundPath = p;
        //this.setCueName(soundPath); // user may want custom name other than file path
    }

    public Path getFilePath() {
        return this.soundPath;
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
