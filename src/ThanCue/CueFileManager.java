package ThanCue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ryan on 18/05/16.
 */
public class CueFileManager {
    public CueFileManager() {

    }

    public void writeCue(String filePath, List<Cue> cueCollection) throws Exception{
        File f = new File(filePath);
        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(f));
        ZipEntry e = new ZipEntry(file);
        stream.putNextEntry(e);
        FileWriter fr = new FileWriter(filePath);
        PrintWriter pw = new PrintWriter(fr);
        for(Cue c : cueCollection) {
            pw.println(c.getFileString()); //this string obtained from the cue class/version
        }




        stream.write(stuff);
        stream.closeEntry();
        stream.close();
    }


    public ZipEntry createEntryFromCue(Cue c) {
        ZipEntry entry = new ZipEntry();


    }
}
