package ThanCue.Variables;

/**
 * This class is used to store all constants related to ThanCue. For non-constant values, store them in Environment.
 * All members must be public, static and final!
 *
 * Created by ryan on 28/05/16.
 */
public class Constants {
    //updates
    public static final String VERSION_NAME = "0.7.2";
    public static final int RELEASE_ID = 23;



    //generic
    public static final String FILE_PATH_NOT_PRESENT = "n/a";
    public static final String DEFAULT_CUE_NAME = "Unnamed cue";

    //progress
    public static final int UPDATE_DELAY_prgDelay = 10;
    public static final int UPDATE_DELAY_prgDuration = 10;

    //URLs
    public static final String URL_GITHUB_THANCUE = "https://www.github.com/OhmGeek/ThanCue/";
    public static final String URL_GITHUB_MIKE = "https://www.github.com/MikeCroall/";
    public static final String URL_GITHUB_RYAN = "https://www.github.com/OhmGeek/";

    //Undo/Redo
    public static final int NUMBER_OF_UNDOS = 100;
    public static final int NUMBER_OF_REDOS = 100;

    //about thancue
    public static final String ABOUT_TEXT = "ThanCue " + VERSION_NAME + " - for the music\n" +
            "Cue software designed by an Abba fan and someone who isn't as keen on Abba\n\n" +
            "Free, open source, simple to use, linux (and maybe mac) supported!\n\n" +
            "By Mike Croall and Ryan Collins"; // todo improve this about text
}
