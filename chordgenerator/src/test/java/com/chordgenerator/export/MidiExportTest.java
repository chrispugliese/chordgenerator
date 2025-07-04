package com.chordgenerator.export;

import org.jfugue.pattern.Pattern;
import com.chordgenerator.export.MidiExporter;

public class MidiExportTest {
        public static void main(String[] args) {
        // Create a JFugue pattern with chords
        Pattern pattern = new Pattern("Cmajw Dmajw Gmajw Aminw"); // 'w' = whole note
        MidiExporter.exportAsFormat1(pattern, "output.mid", 1); // Added third argument as required
    }
    
}
