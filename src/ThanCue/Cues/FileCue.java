package ThanCue.Cues;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mike on 08/06/16.
 */
public abstract class FileCue extends Cue {

    protected transient Path soundPath;

    public FileCue() {
        super();
        this.setCueFilePath("/default/path/to/cue.cue"); // let's keep the youtube links for child cues, not to be inherited
    }

    @Override
    public void setCueStartPoint(int time) {
        cueStartPoint.set(time);
        findTotalPlayTime();
    }

    @Override
    public void setCueDuration(int time) {
        cueDuration.set(time);
        findTotalPlayTime();
    }

    public Path getFilePath() {
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
    public void setCueFilePath(Path path) { //for table
        this.soundPath = path;
        cueFilePath.set(path.toAbsolutePath().toString());
        findTotalPlayTime();
    }

    public long findTotalPlayTime() {
        long foundDuration = -1;

        System.out.println("Finding duration of " + getCueFilePath());

        // find file and duration
        File file = new File(getCueFilePath());
        if (file.exists()){
            System.out.println("File found");
            // read metadata
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                double durationInSeconds = (audioInputStream.getFrameLength() + .0) / format.getFrameRate();
                foundDuration = (long)(1000 * durationInSeconds);
                System.out.println("Found duration: " + foundDuration + "ms");
            }catch(IOException ex) {
                System.out.println("Error in loading duraton of file (assuming sound)");
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("Unsupported audio file in loading duration of file");
                ex.printStackTrace();
            }catch (Exception ex){
                System.out.println("Unknown exception loading duration of file (assuming sound)");
                ex.printStackTrace();
            }
            //todo support video durations too
        }
        foundDuration -= getCueStartPoint();
        if (foundDuration == -1) { // no such file
            totalPlayTime = -1;
        } else if (getCueDuration() <= 0) { // file and play to end
            totalPlayTime = foundDuration;
        } else { // file and play to given duration
            totalPlayTime = getCueDuration() >= foundDuration ? foundDuration : getCueDuration();
        }
        System.out.println("Total play time set to: " + totalPlayTime);
        return totalPlayTime;
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
