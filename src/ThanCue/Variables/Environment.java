package ThanCue.Variables;

import java.io.File;
import java.util.List;

/**
 * This class stores environmental variables (such as temp folder lists), and other non-constant entities that are required to be stored.
 *
 *
 * I'm not entirely happy with this, so todo: make sure it's not abused and look for alternatives!
 *
 * For constants, store them in Constants instead. Everything should be public static in here, and NOT final!
 *
 * Created by ryan on 02/07/16.
 */
public class Environment {
    public static List<File> tempDirectories;
    public static String operatingSystem;


}
