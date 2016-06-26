package ThanCue;

/**
 * Created by ryan on 28/05/16.
 */
public class Constants {
    //updates
    public static final String VERSION_NAME = "0.7.2";
    public static final int RELEASE_ID = 23;

    //file saving/loading
    public static final String endRecordCharacter = String.valueOf((char) 30);
    public static final String endFieldCharacter = String.valueOf((char) 31);

    //generic
    public static String filePathNotPresent = "n/a";
    public static String defaultCueName = "Unnamed cue";

    //URLs
    public static String URL_GITHUB_THANCUE = "https://www.github.com/OhmGeek/ThanCue/";
    public static String URL_GITHUB_MIKE = "https://www.github.com/MikeCroall/";
    public static String URL_GITHUB_RYAN = "https://www.github.com/OhmGeek/";

    //about thancue
    public static String aboutText = "ThanCue " + VERSION_NAME + " - for the music\n" +
            "Cue software designed by an Abba fan and someone who isn't as keen on Abba\n\n" +
            "Free, open source, simple to use, linux (and maybe mac) supported!\n\n" +
            "By Mike Croall and Ryan Collins\n\n" +
            "Thancue: https://github.com/OhmGeek/ThanCue/\n" +
            "Mike Croall: https://github.com/MikeCroall/\n" + // todo make hyperlinks work
            "Ryan Collins: https://github.com/OhmGeek/"; // todo improve this about text
}
