package com.chordgenerator.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.jfugue.player.Player;

import com.chordgenerator.export.MidiExporter;
import com.chordgenerator.generators.ChordGenerator;
import com.chordgenerator.generators.ChordProgressionGenerator;

import java.io.File;
import java.util.List;

import org.jfugue.pattern.Pattern;

public class ChordGeneratorApp extends Application {

	private List<String[]> currentChords;

@Override
public void start(Stage primaryStage) {
	Label outputLabel = new Label("Waiting for input...");

	// ComboBoxes
	ComboBox<String> rootNoteBox = new ComboBox<>();
	rootNoteBox.getItems().addAll("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
	rootNoteBox.setValue("Choose Root Note");

	ComboBox<String> scaleTypeBox = new ComboBox<>();
	scaleTypeBox.getItems().addAll(ChordGenerator.getAvailableScaleTypes());
	scaleTypeBox.setValue("Choose Scale");

	ComboBox<String> progressionTypeBox = new ComboBox<>();
	progressionTypeBox.getItems().addAll(ChordProgressionGenerator.getAvailableProgressions());
	progressionTypeBox.setValue("Choose Genre");

	ComboBox<String> progressionTemplateBox = new ComboBox<>();
	progressionTemplateBox.setDisable(true);

	// Update templates when a progression type is selected
	progressionTypeBox.setOnAction(e -> {
		String selectedGenre = progressionTypeBox.getValue();
		progressionTemplateBox.getItems().clear();
		progressionTemplateBox.getItems().addAll(ChordProgressionGenerator.getProgressionTemplates(selectedGenre));
		progressionTemplateBox.setDisable(false);
		if (!progressionTemplateBox.getItems().isEmpty()) {
			progressionTemplateBox.setValue(progressionTemplateBox.getItems().get(0));
		}
	});

	// Buttons
	Button generateButton = new Button("Generate");
	Button playButton = new Button("Play");
	Button downloadButton = new Button("Download MIDI");


	generateButton.setOnAction(e -> {
		String genre = progressionTypeBox.getValue();
		if (genre == null || genre.isEmpty()) {
			outputLabel.setText("Please select a progression genre.");
			return;
		}

		int selectedTemplateIndex = progressionTemplateBox.getSelectionModel().getSelectedIndex();
		if (selectedTemplateIndex == -1) {
			outputLabel.setText("Please select a progression template.");
			return;
		}

		List<String[]> templates = ChordProgressionGenerator.PROGRESSION_TEMPLATES.get(genre.toLowerCase());
		if (templates == null || selectedTemplateIndex >= templates.size()) {
			outputLabel.setText("Invalid template selection.");
			return;
		}

		try {
			currentChords = ChordProgressionGenerator.generateProgression(
				rootNoteBox.getValue(),
				scaleTypeBox.getValue(),
				genre,
				selectedTemplateIndex
			);

			StringBuilder sb = new StringBuilder("Chords:\n");
			for (String[] chord : currentChords) {
				sb.append(String.join("-", chord)).append("\n");
			}
			outputLabel.setText(sb.toString());
		} catch (Exception ex) {
			outputLabel.setText("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	});

	playButton.setOnAction(e -> {
		if (currentChords == null || currentChords.isEmpty()) {
			outputLabel.setText("Generate a chord progression first.");
			return;
		}

		StringBuilder patternBuilder = new StringBuilder();
		for (String[] chord : currentChords) {
			// JFugue chord: notes joined by "+" followed by duration
			patternBuilder.append(String.join("+", chord)).append("w "); // 'w' = whole note
		}

		Pattern pattern = new Pattern(patternBuilder.toString().trim());
		try {
			Player player = new Player();
			player.play(pattern);
		} catch (Exception ex) {
			outputLabel.setText("Failed to play: " + ex.getMessage());
			ex.printStackTrace();
		}
	});

    downloadButton.setOnAction(e -> {
		if (currentChords == null || currentChords.isEmpty()) {
			outputLabel.setText("Generate a chord progression first.");
			return;
		}

		Pattern pattern = new Pattern("V0 I[Piano]");
		for (String[] chord : currentChords) {
			StringBuilder chordBuilder = new StringBuilder();
			for (int i = 0; i < chord.length; i++) {
				chordBuilder.append(chord[i]).append("w");
				if (i < chord.length - 1) chordBuilder.append("+");
			}
			pattern.add(chordBuilder.toString()).add(" ");
		}

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Melody As...");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("MIDI files (*.mid)", "*.mid"));

        String root = rootNoteBox.getValue();
        String scale = scaleTypeBox.getValue();
        String timestamp = java.time.LocalDateTime.now().toString().replaceAll("[:.]", "-");
        fileChooser.setInitialFileName("chord_progression_" + root + "_" + scale + "_" + timestamp + ".mid");

        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
				
                boolean success = MidiExporter.exportAsFormat1(pattern, file.getAbsolutePath());

                if (success) {
                    outputLabel.setText("Saved to: " + file.getAbsolutePath());
                } else {
                    outputLabel.setText("Could not export MIDI file");
                }
            } catch (NumberFormatException ex) {
                outputLabel.setText("Error: Invalid BPM value");
            }
        } else {
            outputLabel.setText("Save canceled.");
        }
    });

	// Layout
	VBox layout = new VBox(10,
		new Label("Root Note:"), rootNoteBox,
		new Label("Scale Type:"), scaleTypeBox,
		new Label("Progression Type:"), progressionTypeBox,
		new Label("Progression Template:"), progressionTemplateBox,
		generateButton,
		playButton,
		downloadButton,
		outputLabel
	);	
	HBox buttons = new HBox(10, generateButton, playButton, downloadButton);
	layout.getChildren().add(buttons);
	layout.setPadding(new Insets(20));

	Scene scene = new Scene(layout, 400, 750);
	primaryStage.setTitle("Chord Generator");
	primaryStage.setScene(scene);
	primaryStage.show();
}

        public static void main(String[] args){
        launch(args);
    }
}
