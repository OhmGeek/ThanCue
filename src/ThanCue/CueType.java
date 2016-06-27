package ThanCue;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Created by mike on 07/06/16.
 */
public enum CueType {
    SOUND,
    VIDEO,
    LIGHT,
    VOICE,
    UNKNOWN;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
