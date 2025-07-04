package com.chordgenerator.generators;

import java.util.Arrays;
import java.util.List;

public class ChordProgressionGeneratorTest {
        public static void main(String[] args) {
        String root = "D#";
        String mode = "Natural Minor (Aeolian)";
        String genre = "classical";
        int index = 3;       // 0 → the I–V–vi–IV progression

        // 1) show the raw Roman‐numerals template from the map
        String[] raw = ChordProgressionGenerator.PROGRESSION_TEMPLATES
                        .get(genre.toLowerCase())
                        .get(index);

        System.out.println("Raw Roman template for \"" 
                        + genre 
                        + "\" #"+ index 
                        + ": " 
                        + Arrays.toString(raw));

        // 2) convert it to chords
        List<String[]> chords = ChordProgressionGenerator.generateProgression(root, mode, genre, index);

        // 3) print the 4 chord‐arrays
        System.out.println("→ Chords built from that template:");
        for (int i = 0; i < chords.size(); i++) {
            System.out.printf("   %d: %s%n", 
                i, 
                Arrays.toString(chords.get(i))
            );
        }
    }
    
}
