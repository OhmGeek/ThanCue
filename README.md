# ThanCue #
### For the music ###
Cue software designed by an Abba fan and someone who isn't as keen on Abba.

# Development Team
This software is being developed jointly by [Mike Croall](https://www.github.com/MikeCroall "Mike Croall") and [Ryan Collins](https://www.github.com/OhmGeek "Ryan Collins").

# Usage
In terminal:

    java -jar /path/to/dev.jar
OR 
Make the jar executable...

    chmod +x /path/to/dev.jar
and simply double click it!

To use the script to change release id and name, open a terminal in the main repo directory.
Make sure the script is executable:

    chmod +x cv.sh
Run the script:

    ./cv.sh MyNewVersionNameNoSpaces VersionIdAsAnInteger
and check your files updated!

Please note that VLC is now supported, providing the VLC command in terminal works correctly. Tested on Ubuntu, but Mac should work too (although currently untested on this OS).

### Planned Features
#### Audio Cues
- Display duration, description, elapsed time, and remaining time
- Set volume, pitch, fade in and fade out effects
- Set start and end positions in a given track (avoids opening it in some sound editing software)
- Built in stopwatch and clock for timing cues/show operation (more for Show Mode)
- Hotkeys for each cue
- EQ
- MIDI control
- Loops
- Change sound card playback (for each cue)

#### Video Cues
- ASCII Output (yes, VLC does allow this!)
- Video effects
- Same type of things for audio, but for video as well

#### Other
- Updates (partially implemented, but this needs a partial rewrite)
- Check for VLC on installation
- Mac support
- Add custom cue support (using Bash scripts).
- Make the program itself be relatively lightweight when playing many cues!

### Features implemented
- A larger Go Button
- Cue Numbering (useful for sound cues on a script)
- VLC support
- JavaFX rewrite
- File saving
- Cue reordering
- Basic show mode
- Drag n drop cue creation
