package com.chordgenerator.export;

import org.jfugue.pattern.Pattern;
import org.jfugue.midi.MidiFileManager;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;


public class MidiExporter {

    public static boolean exportAsFormat1(Pattern pattern, String filename, int bpm){
        try{

            // convert jfugue pattern to midi sequence

            File tempFile = File.createTempFile("temp_melody", ".mid");
            MidiFileManager.savePatternToMidi(pattern, tempFile);

            Sequence original = MidiSystem.getSequence(tempFile);

            // create a new sequence in format 1 (2 tracks)
            Sequence format1 = new Sequence(Sequence.PPQ,  original.getResolution(), 2);

            // create a track and copy events from original
            Track source = original.getTracks()[0];
            Track melodyTrack = format1.createTrack();

            // Add tempo event at the beginning
            int tempo = 60000000 / bpm; // Convert BPM to microseconds per quarter note
            MetaMessage tempoMessage = new MetaMessage();
            tempoMessage.setMessage(0x51, new byte[]{(byte)(tempo >> 16), (byte)(tempo >> 8), (byte)tempo}, 3);
            melodyTrack.add(new MidiEvent(tempoMessage, 0));

            for (int i = 0; i < source.size(); i++){
                melodyTrack.add(source.get(i));
            }

            // write to file
            File midiFile = new File(filename);
            MidiSystem.write(format1, 1, midiFile);

            System.out.println("Exported MIDI in Format 1 to: " + midiFile.getAbsolutePath() + " with BPM: " + bpm);
            return true;
        } catch (IOException | InvalidMidiDataException e){
            System.err.println("Failed to export MIDI: " + e.getMessage());
            return false;
        }
    }

    // Keep the old method for backward compatibility
    public static boolean exportAsFormat1(Pattern pattern, String filename){
        return exportAsFormat1(pattern, filename, 120); // Default to 120 BPM
    }
    
}
