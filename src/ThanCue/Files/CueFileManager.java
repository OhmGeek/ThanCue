package ThanCue.Files;

import ThanCue.Cues.Cue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ThanCue.Cues.FileCue;
import ThanCue.Exceptions.InvalidFileTypeException;
import ThanCue.Variables.Environment;
import org.zeroturnaround.zip.*;
import org.zeroturnaround.zip.commons.FileUtils;

/**
 * Created by ryan on 18/05/16.
 */
public class CueFileManager {

    public List<Cue> readCue(File zipFile) throws Exception {
        System.out.println(zipFile.getPath().toString());

        List<Cue> cuesLoaded = new ArrayList<>();

        //GAMEPLAN:
        //  1. Create a new ZipFile, and get enumeration of entries in the zipfile
        //  2. Find the index.dat file, and parse it.
        //      i) Create a new Cue based upon each entry; and
        //      ii) Copy the corresponding file (if a filecue) into tmp memory, and store a pointer to this
        //  3. Repeat for each file in the index.dat :)

        if(!ZipUtil.containsEntry(zipFile,"cueCollection.ser")) {
            System.out.println("Invalid File");
            throw new InvalidFileTypeException();
        }
        Path dest = Files.createTempDirectory(zipFile.toPath().getParent(),""); //TODO make hidden directory and change to use of files throughout

        ZipUtil.unpack(zipFile,dest.toFile());

        Path index = Paths.get(dest.toString() + "/index.dat");

        File[] filesToRead = dest.toFile().listFiles();
        for (File file : filesToRead) {
            if(file.getName().equals("cueCollection.ser")) {
                try {
                    FileInputStream input = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(input);
                    cuesLoaded = (List<Cue>) in.readObject();
                    in.close();
                    input.close();
                } catch(Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in reading");
                }

            }
            else {
                //copy files across
                Files.copy(file.toPath(),Paths.get(dest.toString() + "/" + file.getName()));
            }
        }

        //now we've copied files over, go through and adjust the paths appropriately. Add the temp folder in to the correct place.
        cuesLoaded.forEach(c -> {
            String oldFilePath = c.getCueFilePath();
            c.setCueFilePath(Paths.get(dest.toString() + "/" + oldFilePath));
        });



        Environment.tempDirectories.add(dest.toFile());
        return cuesLoaded;
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

        //recursively delete directory and all stuff inside
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }





}
