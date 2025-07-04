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

		
		// TODO: Add your JavaFX application initialization code here
		primaryStage.setTitle("Chord Generator");
		primaryStage.show();
	}
        public static void main(String[] args){
        launch(args);
    }
}
