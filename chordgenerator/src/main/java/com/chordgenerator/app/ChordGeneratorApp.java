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

import com.chordgenerator.generators.ChordGenerator;

import org.jfugue.pattern.Pattern;

public class ChordGeneratorApp extends Application {

	@Override
	public void start(Stage primaryStage) {

		final String[] chordText ={""};
		final ChordGenerator[] generator = {new ChordGenerator()};

		Label outputLabel = new Label("Waiting for input...");

		ComboBox<String> rootNoteBox = new ComboBox<>();
        rootNoteBox.getItems().addAll("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
        rootNoteBox.setValue("C");

		ComboBox<String> scaleTypeBox = new ComboBox<>();
        scaleTypeBox.getItems().addAll(ChordGenerator.getAvailableScaleTypes());
        scaleTypeBox.setValue("Dorian");


		// TODO: Add your JavaFX application initialization code here
		        VBox layout = new VBox(10,
            new Label("Root Note:"), rootNoteBox,
            new Label("Scale Type:"), scaleTypeBox

        );
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
