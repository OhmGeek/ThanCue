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
        System.out.println(zipFile.getPath().toString());

        List<Cue> cuesLoaded = new ArrayList<>();

        //GAMEPLAN:
        //  1. Create a new ZipFile, and get enumeration of entries in the zipfile
        //  2. Find the index.dat file, and parse it.
        //      i) Create a new Cue based upon each entry; and
        //      ii) Copy the corresponding file (if a filecue) into tmp memory, and store a pointer to this
        //  3. Repeat for each file in the index.dat :)

        if(!ZipUtil.containsEntry(zipFile,"index.dat")) {
            System.out.println("Invalid File");
            throw new InvalidFileTypeException();
        }
        File dest = new File("/tmp/ThanCue"); //todo change to offset of current file

        ZipUtil.unpack(zipFile,dest);

        Path index = Paths.get(dest.toString() + "/index.dat");
        //now read through the file
        Files.lines(index).forEach(s -> {

            String[] fields = s.split(Constants.endFieldCharacter);





        });
        return null;
    }




//    public List<Cue> readCue(File zipFile) throws Exception {
//        System.out.println(zipFile.getPath().toString());
//        //todo refactor and make readCue nicer. It's currently awful. Also make sure it works :)
//        String endField = String.valueOf((char) 30);
//        List<Cue> cuesLoaded = new ArrayList<>();
//
//        ZipFile f = new ZipFile(zipFile.getPath());
//        Enumeration<? extends ZipEntry> files = f.entries();
//        if (f == null) {
//            System.out.println("Files not in zip file");
//            throw new FileNotFoundException();
//        }
//
//        while (files.hasMoreElements()) {
//            ZipEntry e = files.nextElement();
//
//            if (e.getName().contains("index.dat")) {
//                //read the file and load it in.
//                InputStream stream = f.getInputStream(e);
//                BufferedReader r = new BufferedReader(new InputStreamReader(stream));
//                String record = r.readLine();
//                if (record == null) {
//                    throw new NoSuchElementException();
//                }
//                do {
//
//                    if (record != null) {
//                        String[] arrayOfFields = record.split(endField);
//                        if (arrayOfFields.length <= 1)
//                            throw new FileNotFoundException();
//
//                        //todo use switch instead
//                        if (arrayOfFields[0].equals("sc")) {
//                            //sound cue
//                            SoundCue c = new SoundCue();
//                            c.setCueName(arrayOfFields[1]);
//                            //c.setCueType(arrayOfFields[2]); //todo fix cue type
//                            c.setCueName(arrayOfFields[3]);
//                            File associatedFile = zipFile.createTempFile();
//                            c.setCueFilePath("zip:///" + zipFile.getParent().toString() + arrayOfFields[4]);
//                            cuesLoaded.add(c);
//                        }
//                    }
//                    record = r.readLine();
//                } while (record != null);
//            }
//        }
//        return cuesLoaded;
//    }


    public void writeCue(File destination, List<Cue> cueCollection) throws Exception {
       //plan:
        //create new temp folder. This stores the serialised list and also the dependency files (used to play cues)
        //we then compress this using ZipUtil.pack
        //for each of these, we simply serialise everything (namely the list
        //this creates a new file :)

        //1. create tmp folder
        File temp = File.createTempFile("ThanCue-Save-File","");







        cueCollection.forEach(c -> {
            try {
                if (c instanceof FileCue) {

                    Path filePath = Paths.get(temp.getPath() + "/" + ((FileCue) c).getFilePath().getFileName().toString());
                    System.out.println(filePath);
                    Files.copy(((FileCue) c).getFilePath(), filePath);
                }
            }
            catch(Exception ex) {
                System.out.println("Error in copying files");
                ex.printStackTrace();
            }
        });





        //now delete the temporary folder
        temp.delete();
        temp.mkdir();

    }





}
