package com.chordgenerator.generators;

import java.util.*;

public class ChordProgressionGenerator {


    public static final Map<String, Integer> ROMAN_TO_DEGREE = Map.ofEntries(
        Map.entry("I", 0),
        Map.entry("ii", 1),
        Map.entry("iii", 2), 
        Map.entry("IV", 3),
        Map.entry("V", 4), 
        Map.entry("vi", 5),
        Map.entry("vii", 6),
        Map.entry("vii°", 6),
        Map.entry("i", 0),
        Map.entry("II", 1),
        Map.entry("III", 2),
        Map.entry("iv", 3),
        Map.entry("v", 4),
        Map.entry("VI", 5),
        Map.entry("VII", 6)
    );

    public static final Map<String, List<String[]>> PROGRESSION_TEMPLATES = new HashMap<>(Map.of(
        "pop", List.of(
            new String[]{"I", "V", "vi", "IV"},
            new String[]{"vi", "IV", "I", "V"},
            new String[]{"I", "vi", "IV", "V"},
            new String[]{"I", "IV", "V", "IV"}
        ),

        "rock", List.of(
            new String[]{"I", "IV", "V", "I"},
            new String[]{"i", "VI", "iii", "vii"}

        ),
        "r&b", List.of(
            new String[]{"ii", "V", "I"},
            new String[]{"IV", "V", "iii", "vi"},
            new String[]{"I", "iii", "IV", "iv"}

        ),
        "jazz", List.of(
            new String[]{"ii", "V", "I"},
            new String[]{"I", "vi", "ii", "V"}
        ),
        "classical", List.of(
            new String[]{"I", "IV", "V", "I"},
            new String[]{"I", "vi", "ii", "V"}, 
            new String[]{"vi", "ii", "V", "I"}, 
            new String[]{"I", "V", "vi", "iii", "IV", "I", "IV", "V"}
        )        
    ));

    public static List<String[]> generateProgression(String rootNote, String scaleType, String genre, int templateIndex) {

        List<String[]> templates = PROGRESSION_TEMPLATES.get(genre.toLowerCase());
        if (templates == null) {
            throw new IllegalArgumentException("Unsupported genre: " + genre);
        }
        if (templateIndex < 0 || templateIndex >= templates.size()) {
            throw new IllegalArgumentException("Template index out of bounds: " + templateIndex);
        }

        // now pick *that* one, not a random one
        String[] chosenProgression = templates.get(templateIndex);

        // (the rest is identical to your original)
        String[] scale = ChordGenerator.generateScale(rootNote, scaleType);
        List<String> scaleList = Arrays.asList(scale);
        List<String[]> progressionChords = new ArrayList<>();

        for (String roman : chosenProgression) {
            Integer degree = ROMAN_TO_DEGREE.get(roman);
            if (degree == null) {
                throw new IllegalArgumentException("Unsupported chord: " + roman);
            }
            String[] chord = ChordGenerator.buildChord(scaleList.get(degree), "Major");
            progressionChords.add(chord);
        }

        return progressionChords;
    }
    public static void main(String[] args) {
        String root = "D#";
        String mode = "Natural Minor (Aeolian)";
        String genre = "classical";
        int index = 3;       // 0 → the I–V–vi–IV progression

        // 1) show the raw Roman‐numerals template from the map
        String[] raw = PROGRESSION_TEMPLATES
                        .get(genre.toLowerCase())
                        .get(index);

        System.out.println("Raw Roman template for \"" 
                        + genre 
                        + "\" #"+ index 
                        + ": " 
                        + Arrays.toString(raw));

        // 2) convert it to chords
        List<String[]> chords = generateProgression(root, mode, genre, index);

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

