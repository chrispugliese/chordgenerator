package com.chordgenerator.generators;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChordProgressionGeneratorTest {

@Test
void testGenerateFixedPopProgressionInCMajor() {
    ChordProgressionGenerator.TEST_OVERRIDE_TEMPLATE(
        Arrays.asList(
            new String[]{"I", "V", "vi", "IV"}
        ), "testpop"
    );

    List<String[]> chords = ChordProgressionGenerator.generateProgression("C", "Major (Ionian)", "testpop");

    assertEquals(4, chords.size());
    assertArrayEquals(new String[]{"C", "E", "G"}, chords.get(0));
    assertArrayEquals(new String[]{"G", "B", "D"}, chords.get(1));
    assertArrayEquals(new String[]{"A", "C", "E"}, chords.get(2));
    assertArrayEquals(new String[]{"F", "A", "C"}, chords.get(3));
}

    @Test
    void testInvalidGenreThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ChordProgressionGenerator.generateProgression("C", "Major (Ionian)", "jungle");
        });
    }

    @Test
    void testInvalidNumeralThrowsException() {
        // Temporarily insert a bad numeral to the progression map for testing
        ChordProgressionGenerator.TEST_OVERRIDE_TEMPLATE(
            Arrays.asList(
                new String[]{"I"},
                new String[]{"bVII"},
                new String[]{"IV"}
            ), "test"
        );

        assertThrows(IllegalArgumentException.class, () -> {
            ChordProgressionGenerator.generateProgression("C", "Major (Ionian)", "test");
        });
    }
}
