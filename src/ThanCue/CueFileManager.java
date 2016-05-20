package ThanCue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ryan on 18/05/16.
 */
public class CueFileManager {

    public CueFileManager() {

    }

    public void writeCue(String folder, String zipName,List<Cue> cueCollection) throws Exception{
        File f = new File(folder + zipName + ".cues");
        ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(f));

        //first of all, create our cpf (cue page file)
        FileWriter fr = new FileWriter(folder + "index.dat");
        PrintWriter pw = new PrintWriter(fr);

        //go through all cues now
        //and for each one, write to cpf and then write files.

        for(Cue c : cueCollection) {
            pw.println(c.getFileString()); //write the file string for the cue itself.

            if(c instanceof SoundCue) {
                SoundCue sound = (SoundCue) c;
                //if the file is a sound cue, then also write the corresponding sound file.
                Path path = sound.getFilePath();

                ZipEntry e = new ZipEntry(path.getFileName().toString()); //this needs to use the end part of file path.

                byte[] dataRead = Files.readAllBytes(path);

                zipStream.putNextEntry(e);
                zipStream.write(dataRead,0,dataRead.length);
            }
        }





//        File f = new File(filePath);
//        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(f));
//
//        List<ZipEntry> filesToWrite = new ArrayList<>();
//
//
//
//        ZipEntry e = new ZipEntry(f
//        stream.putNextEntry(e);
//        FileWriter fr = new FileWriter(filePath);
//        PrintWriter pw = new PrintWriter(fr);
//        for(Cue c : cueCollection) {
//            pw.println(c.getFileString()); //this string obtained from the cue class/version
//            addFilesToListIfNecessary(c,filesToWrite);
//        }
//
//        ZipEntry e = new ZipEntry(filePath + "/entries.dat");
//
//        for(ZipEntry e : filesToWrite) {
//            stream.putNextEntry(e);
//            stream.write(l)
//        }
//
//        stream.write(stuff);
//        stream.closeEntry();
//        stream.close();
    }

}
