package com.chordgenerator.generators;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChordGeneratorTest {

    @Test
    void testBuildTriad_CMajorScale_FirstChord() {
        List<String> scale = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
        String[] triad = ChordGenerator.buildTriad(scale, 0); // I chord
        assertArrayEquals(new String[]{"C", "E", "G"}, triad);
    }

    @Test
    void testBuildTriad_CMajorScale_FourthChord() {
        List<String> scale = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
        String[] triad = ChordGenerator.buildTriad(scale, 3); // IV chord
        assertArrayEquals(new String[]{"F", "A", "C"}, triad);
    }

    @Test
    void testGenerateChords_CMajorScale() {
        List<String[]> chords = ChordGenerator.generateChords("C", "Major (Ionian)");

        assertEquals(7, chords.size()); // 7 chords + octave
        assertArrayEquals(new String[]{"C", "E", "G"}, chords.get(0));
        assertArrayEquals(new String[]{"D", "F", "A"}, chords.get(1));
        assertArrayEquals(new String[]{"E", "G", "B"}, chords.get(2));
        assertArrayEquals(new String[]{"F", "A", "C"}, chords.get(3));
        assertArrayEquals(new String[]{"G", "B", "D"}, chords.get(4));
        assertArrayEquals(new String[]{"A", "C", "E"}, chords.get(5));
        assertArrayEquals(new String[]{"B", "D", "F"}, chords.get(6));
    }

    @Test
    void testGenerateChords_InvalidRootNote() {
        assertThrows(IllegalArgumentException.class, () -> {
            ChordGenerator.generateChords("H", "Major (Ionian)");
        });
    }

    @Test
    void testGenerateChords_InvalidScaleType() {
        assertThrows(IllegalArgumentException.class, () -> {
            ChordGenerator.generateChords("C", "Lunar Scale");
        });
    }
}
