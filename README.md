# ThanCue #
### For the music ###
Cue software designed by an Abba fan and someone who isn't as keen on Abba.

# Development Team
This software is being developed jointly by Mike Croall and Ryan Collins.

# Usage
In terminal:

    java -jar /path/to/dev.jar
OR 
Make the jar executable...

    chmod +x /path/to/dev.jar
and simply double click it!

### Planned Features
#### Show Mode
- Back/Pause button, in case someone accidentally presses go

#### General
- Ability to change the windows (allowing for different displays inside the main window)
- Play using VLC
- Multiple tracks play at a time
- Drop down menu for changing cue behaviour (in table entry's behaviour cell)
- Sound cue can start and end at different points (start start point and duration, loop if start + duration is after end of clip)
- Sound cue can fade in/out within a certain time
- Cue Numbering independent of the view (per cue)
- Saving cues in a zip file structure (including the sound files themselves)
- Undo/Redo engine for editing shows
- Move cues by dragging and dropping cues into the correct place
- Implement some type of window manager in order to control the environment (set title, create dialogs, etc)

Many more features could still be added, but they aren't currently in the list.


### Features implemented
- A larger Go Button
- Cue Numbering (useful for sound cues on a script)
