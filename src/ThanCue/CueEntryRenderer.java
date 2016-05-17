package ThanCue;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mike on 17/05/16.
 */
class CueEntryRenderer extends JLabel implements ListCellRenderer {
        private static final Color HIGHLIGHT_COLOUR = new Color(184, 207, 229);
        private static final Color BORDER_COLOUR = new Color(144, 167, 189);

        public CueEntryRenderer() {
            setOpaque(true);
            setIconTextGap(12);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Cue cue = (Cue) value;
            setText(cue.getCueType() + " - " + cue.getCueName());
            setIcon(cue.getIcon());
            if (isSelected) {
                setBackground(HIGHLIGHT_COLOUR);
                setBorder(BorderFactory.createLineBorder(BORDER_COLOUR));
                setForeground(Color.white);
            } else {
                setBackground(Color.white);
                setBorder(BorderFactory.createEmptyBorder());
                setForeground(Color.black);
            }
            return this;
        }
}
