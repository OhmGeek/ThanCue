package ThanCue.VLC;

import ThanCue.Variables.Constants;
import ThanCue.Variables.Environment;
import sun.awt.OSInfo;
/**
 * Created by ryan on 11/06/16.
 */
public class VLCMusicPlayer {
    private Process vlcInstance;
    private String fileToPlayURL;
    private int startPoint, duration;

    public VLCMusicPlayer(String fileToPlayURL, int startPoint, int duration) {

        vlcInstance = null; //stores the vlc process. Ignore for now
        this.fileToPlayURL = fileToPlayURL;
        this.startPoint = -1 * startPoint; // negative means advance through track, as we are using the delay argument in the command line
        this.duration = duration;
    }

    private void linuxPlay() {


        //todo deal with exceptions
        try {
            vlcInstance = Runtime.getRuntime().exec(new String[]{"vlc","-I dummy", fileToPlayURL, "--sout-delay-delay=" + startPoint,"--play-and-exit"}); // todo USE duration
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    private void windowsPlay() {

        //todo windows code here
        //todo exceptions
        linuxPlay();
    }

    private void macPlay() {

        //todo deal with exceptions properly
        try {
            vlcInstance = Runtime.getRuntime().exec(new String[]{"/Applications/VLC.app/Contents/MacOS/VLC","-I dummy", fileToPlayURL, "--sout-delay-delay=" + startPoint,"--play-and-exit"}); // todo USE duration
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }




    }
    public void play() {
        //todo exceptions
        switch(Environment.operatingSystem) {

            case WINDOWS:
                System.out.println("Play on windows");
                windowsPlay();
                break;
            case LINUX:
                System.out.println("Linux Play");
                linuxPlay();
                break;
            case SOLARIS:
                System.out.println("Solaris isn't currently supported properly");
                linuxPlay();
                break;
            case MACOSX:
                System.out.println("Mac Play");
                macPlay();
                break;
            case UNKNOWN:
                System.out.println("Unknown: Assume some type of nix system");
                linuxPlay();
                break;
        }





    }

    public void stop() {
        vlcInstance.destroy(); //end the process
    }

}
