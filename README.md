# ThanCue #
### For the music ###
Cue software designed by an Abba fan and someone who isn't as keen on Abba.

# Development Team
This software is being developed jointly by [Mike Croall](https://www.github.com/MikeCroall "Mike Croall") and [Ryan Collins](https://www.github.com/OhmGeek "Ryan Collins").

# Usage
First, install the required dependencies:
    
    sudo apt-get install vlc

Now, download the jar file. In terminal:

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


### Planned Features
#### Audio Cues
- Display duration, description, elapsed time, and remaining time **(Mike)
- Set volume, pitch, fade in and fade out effects
- Set start and end positions in a given track (avoids opening it in some sound editing software)
- Built in stopwatch and clock for timing cues/show operation (more for Show Mode)
- Hotkeys for each cue
- EQ
- MIDI control
- Loops
- Change sound card playback (for each cue)
- Mac Voice Cue (using the say command)
- More powerful VLC interface (allowing all the things above, and more!)
- 
#### Video Cues
- ASCII Output (yes, VLC does allow this!)
- Video effects
- Same type of things for audio, but for video as well

#### Other
- Updates (partially implemented, but this needs a partial rewrite)
- Ability to Undo and Redo thing **(Ryan)
- Check for VLC on installation
- Mac support
- Add custom cue support (using Bash scripts).
- Make the program itself be relatively lightweight when playing many cues!
- Cue numbers on the go button
- Pausing of cues, and ability to go back to a previous cue (both previous, as well as any cue through a goto cue button)

### Features implemented
- A larger Go Button
- Cue Numbering (useful for sound cues on a script)
- VLC support
- JavaFX rewrite
- File saving (loading sort of works as well, but sound cue paths don't yet...)
- Cue reordering
- Basic show mode
- Drag n drop cue creation
- Voice Cues using Espeak


** denotes a task actively being worked on. The name in brackets is the person working on it.
