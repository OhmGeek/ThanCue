package ThanCue.VLC;

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
            //todo quit after playing: vlc://quit or something like that
            //todo allow more properties to be created
            //do something. Currently uses mplayer which works, but VLC should work once we
            //fix it.
            vlcInstance = Runtime.getRuntime().exec(new String[]{"vlc","-f",fileToPlayURL, "--play-and-exit"});
        }
        catch(Exception ex) {

        }

    }

    private void windowsPlay() {
        linuxPlay();
    }
    private void macPlay() {
        linuxPlay();
    }
    public void play() {

        //todo use an ENUM here with switch. it'll be nicer!
        //todo exceptions here to be thrown if errors occur
        if(operatingSysName.startsWith("Windows")) {
            windowsPlay();
        }
        else if(operatingSysName.contains("mac os") || operatingSysName.contains("macos") || operatingSysName.contains("darwin")) {
            macPlay();
        }
        else if(operatingSysName.contains("nix") || operatingSysName.contains("nux")) {
            linuxPlay();
        }
    }

    public void stop() {

        //todo use an ENUM here with switch. it'll be nicer!
        if(operatingSysName.startsWith("Windows")) {
            vlcInstance.destroy();
        }
        else if(operatingSysName.contains("mac os") || operatingSysName.contains("macos") || operatingSysName.contains("darwin")) {
            vlcInstance.destroy();
        }
        else if(operatingSysName.contains("nix") || operatingSysName.contains("nux")) {
            vlcInstance.destroy();
        }
    }

}
