package ThanCue.VLC;

import ThanCue.Variables.Environment;

import java.nio.file.Path;

/**
 * Created by ryan on 11/06/16.
 */
public class VLCVideoPlayer {
    private Process vlcInstance;
    private String operatingSysName;
    private String fileToPlayURL;


    public VLCVideoPlayer(String fileToPlayURL) {

        vlcInstance = null; //stores the vlc process. Ignore for now
        operatingSysName = System.getProperty("os.name"); //gets the operating system name
        this.fileToPlayURL = fileToPlayURL;
    }

    private void linuxPlay() {
        //todo deal with exceptions
        try {
            //todo allow more properties to be created
            //do something. Currently uses mplayer which works, but VLC should work once we
            //fix it.
          }
        catch(Exception ex) {

        }

    }

    private void windowsPlay() {
        linuxPlay();
    }
    private void macPlay() {

        //todo deal with exceptions
        try {

            //todo allow more properties to be created
            //do something. Currently uses mplayer which works, but VLC should work once we
            //fix it.

            vlcInstance = Runtime.getRuntime().exec(new String[]{"/Applications/VLC.app/Contents/MacOS/VLC","-f",fileToPlayURL, "--play-and-exit"});

        }
        catch(Exception ex) {

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
                System.out.println("Solaris isn't currently supported properly. Try linux play");
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

       vlcInstance.destroy();
    }

}
