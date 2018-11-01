# ThanCue #
**Application no longer supported or developed**

### For the music ###
Cue software designed by an Abba fan and someone who isn't as keen on Abba.

# Development Team
This software is being developed jointly by [Mike Croall](https://www.github.com/MikeCroall "Mike Croall") and [Ryan Collins](https://www.github.com/OhmGeek "Ryan Collins").

# Usage
## Linux
First, install the required dependencies:
    
    sudo apt-get install vlc

Now, download the jar file. In terminal:

    java -jar /path/to/dev.jar
    
OR 

Make the jar executable...

    chmod +x /path/to/dev.jar
and simply double click it!

ThanCue is developed on Ubuntu, and extensively tested for Ubuntu. Let us know if something doesn't work on your distro, and feel free to send a pull request to fix the issue!

## macOS (or MAC OS X as it used to be known)
With MacOS, first ensure you have a JVM installed, along with the latest edition of VLC.

The VLC app MUST be located in the Applications folder on the mac, otherwise ThanCue won't be able to play anything.

Now download the JAR package from our repo. As we are currently an 'unidentified developer', one might have to go into System Preferences and allow the app to run. This is something that we will fix at a later date.

ThanCue for trying out our wonderful program; it's still in development but we hope you like it!


## Versioning Script

To use the script to change release id and name, open a terminal in the main repo directory.
Make sure the script is executable:

    chmod +x cv.sh
Run the script:

    ./cv.sh MyNewVersionNameNoSpaces VersionIdAsAnInteger
and check your files updated!
## Planned Features
### Audio Cues
- Display duration, description, elapsed time, and remaining time **(Mike)
- Set volume, pitch, fade in and fade out effects
- Set start and end positions in a given track (avoids opening it in some sound editing software)
- Built in stopwatch and clock for timing cues/show operation (more for Show Mode)
- Hotkeys for each cue (somewhat implemented, but need to add key combos/actual checking to avoid overwriting important keys)
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

### Other
- Updates (partially implemented, but this needs a partial rewrite)
- Ability to Undo and Redo thing **(Ryan)
- Check for VLC on installation
- Mac support
- Make the program itself be relatively lightweight when playing many cues!
- Cue numbers on the go button
- Pausing of cues, and ability to go back to a previous cue (both previous, as well as any cue through a goto cue button)

## Features implemented

- A larger Go Button
- Cue Numbering (useful for sound cues on a script)
- VLC support
- JavaFX rewrite
- File saving (loading sort of works as well, but sound cue paths don't yet...)
- Cue reordering
- Basic show mode
- Drag n drop cue creation
- Voice Cues using Espeak
- Add custom cue support (using Bash scripts). (needs custom GUI)

## Problems that need addressing

- Mac doesn't appear to update well. Fix this, as it's due to our addition to UpdateFX (that I need to clean up) ** Ryan
- Generally clean up the code, as it's terrible in some cases (particularly drag and drop)
- When working with files, sometimes one gets a 'phantom cue' which you can't play, but also can't touch at all. What's causing this? 

** denotes a task actively being worked on. The name in brackets is the person working on it.
