package com.example.sudoku;

import com.example.sudoku.utils.Path;
import com.example.sudoku.view.StartView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that launches the Sudoku application.
 * This class extends the {@link javafx.application.Application} class and serves
 * as the entry point for the application.
 * @author Juan David Lopez
 * @author Grosman Klein Garcia Valencia
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Main method which is the entry point of the application.
     * It launches the JavaFX application by calling {@link Application#launch(String...)}.
     *
     * @param args Command line arguments (not used in this case).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is called when the application is launched. It initializes
     * the primary stage and loads the initial view of the Sudoku game.
     *
     * @param primaryStage The main window of the application (Stage).
     * @throws Exception if there is an error loading the FXML view or setting up the stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Launches the StartView singleton instance
        StartView.getInstance();
    }
}

