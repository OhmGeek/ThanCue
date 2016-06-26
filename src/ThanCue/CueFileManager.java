package ThanCue;

import javafx.application.Platform;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.zeroturnaround.zip.*;
/**
 * Created by ryan on 18/05/16.
 */
public class CueFileManager {

    public List<Cue> readCue(File zipFile) throws Exception {

        //1. creat

    }

    public void writeCue(File destination, List<Cue> cueCollection) throws Exception {

        //1. create tmp folder
        Path tempDirectory = Files.createTempDirectory(destination.toPath().getParent(),"");

        //2. copy dependencies of cues over (files mainly)
        cueCollection.forEach(c -> {
            try {
                if (c instanceof FileCue) {

                    Path filePath = Paths.get(tempDirectory.toString() + "/" + ((FileCue) c).getFilePath().getFileName().toString());
                    System.out.println(filePath);
                    Files.copy(((FileCue) c).getFilePath(), filePath);
                }
            }
            catch(Exception ex) {
                System.out.println("Error in copying files");
                ex.printStackTrace();
            }
        });

        //3. serialise the list of cues itself (by serialising the cues)
        OutputStream indexFile = null;
        OutputStream buffer = null;
        ObjectOutput output = null;
        try {
            indexFile = new FileOutputStream(tempDirectory.toString() + "/cueCollection.ser");
            buffer = new BufferedOutputStream(indexFile);
            output = new ObjectOutputStream(buffer);

            output.writeObject(new ArrayList<>(cueCollection)); //make an arraylist that is actually serializable
        } catch(IOException ex) {
            System.out.println("IOException");
            ex.printStackTrace();
        }
        finally {
            output.close();
        }



        //4. compress the folder into a zip file (.cues)
        ZipUtil.pack(tempDirectory.toFile(),destination);

        tempDirectory.toFile().deleteOnExit();
    //todo actually delete the temp directory after saving entirely (something I'm not sure is done automatically or not)
    }





}
