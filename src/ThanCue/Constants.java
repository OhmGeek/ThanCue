package ThanCue;

/**
 * Created by ryan on 28/05/16.
 */
public class Constants {
    //updates
    public static final String VERSION_NAME = "0.7.2";
    public static final int RELEASE_ID = 20;

    //file saving/loading
    public static final String endRecordCharacter = String.valueOf((char) 30);
    public static final String endFieldCharacter = String.valueOf((char) 31);

    //generic
    public static String filePathNotPresent = "n/a";
    public static String defaultCueName = "Unnamed cue";

    //about thancue
    public static String aboutText = "ThanCue " + VERSION_NAME + " - for the music\n" +
            "cue software designed by an Abba fan and someone who isn't as keen on Abba\n\n" +
            "Free, open source, simple to use, multi-platform!\n\n" +
            "By Mike Croall and Ryan Collins\n\n" +
            "Thancue: https://github.com/OhmGeek/ThanCue\n" +
            "Mike Croall: https://github.com/MikeCroall/\n" + // todo make hyperlinks work
            "Ryan Collins: https://github.com/OhmGeek/"; // todo improve this about text
}
