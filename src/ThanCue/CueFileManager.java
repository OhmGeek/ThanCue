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
            System.out.println("INvalid File");
            throw new InvalidFileTypeException();
        }
        File dest = new File("/tmp/ThanCue"); //todo change to offset of current file
        ZipUtil.unpack(zipFile,dest);

        Path index = Paths.get(dest.toString() + "/index.dat");
        //now read through the file
        Files.lines(index).forEach(s -> {
            Cue c = new SoundCue();
            String[] fields = s.split(Constants.endFieldCharacter);
            c.setCueName(fields[0]);
            c.setCueType(CueType.valueOf(fields[1]));
            c.setCueBehaviour(CueBehaviour.valueOf(fields[2]));
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


    public void writeCue(String folder, String zipName, List<Cue> cueCollection) throws Exception {
        File f = new File(folder + zipName + ".cues");
        ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(f));

        //first of all, create our cpf (cue page file)
        FileWriter fr = new FileWriter(folder + "index.dat");
        PrintWriter pw = new PrintWriter(fr);

        //go through all cues now
        //and for each one, write to cpf and then write files.

        for (Cue c : cueCollection) {
            pw.println(c.getFileString()); //write the file string for the cue itself.

            if (c instanceof SoundCue) {
                SoundCue sound = (SoundCue) c;
                //if the file is a sound cue, then also write the corresponding sound file.
                Path path = sound.getFilePath();

                ZipEntry e = new ZipEntry(path.getFileName().toString()); //this needs to use the end part of file path.

                byte[] dataRead = Files.readAllBytes(path);

                zipStream.putNextEntry(e);
                zipStream.write(dataRead, 0, dataRead.length);
            }
        }
        pw.close();
        fr.close();
        Path indexPath = Paths.get(folder + "index.dat");
        ZipEntry e = new ZipEntry("index.dat");
        byte[] dataRead = Files.readAllBytes(indexPath);
        zipStream.putNextEntry(e);
        zipStream.write(dataRead, 0, dataRead.length);
        zipStream.close();
    }

}
