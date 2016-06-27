package ThanCue.VLC;

/**
 * Created by ryan on 11/06/16.
 */
public class VLCMusicPlayer {
    private Process vlcInstance;
    private String operatingSysName;
    private String fileToPlayURL;
    private int startPoint, duration;
    public VLCMusicPlayer(String fileToPlayURL, int startPoint, int duration) {

        vlcInstance = null; //stores the vlc process. Ignore for now
        operatingSysName = System.getProperty("os.name"); //gets the operating system name
        this.fileToPlayURL = fileToPlayURL;
        this.startPoint = -1 * startPoint; // negative means advance through track, as we are using the delay argument in the command line
        this.duration = duration;
    }

    private void linuxPlay() {
        //todo deal with exceptions
        try {
            vlcInstance = Runtime.getRuntime().exec(new String[]{"vlc", fileToPlayURL, "--sout-delay-delay=" + startPoint}); // todo USE duration
        }
        catch(Exception ex) {
            ex.printStackTrace();
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
