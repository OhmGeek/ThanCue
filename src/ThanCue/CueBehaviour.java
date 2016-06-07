package ThanCue;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Created by ryan on 18/05/16.
 */
public enum CueBehaviour {
    PLAY_ON_GO,
    PLAY_WITH_PREVIOUS,
    PLAY_AFTER_PREVIOUS;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().replace("_"," ").toLowerCase());
    }
}
