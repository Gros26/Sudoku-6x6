package com.example.sudoku.view;

import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents the "Start View" of the Sudoku game.
 * It is the initial screen shown to the player where they can start the game.
 * The view is created by loading the corresponding FXML file and applying the appropriate stylesheet.
 * The class follows the Singleton design pattern to ensure only one instance of the StartView is active at any given time.
 * @author Juan David Lopez
 * @author Grosman Klein Valencia
 * @version 1.0
 */
public class StartView extends Stage {

    /**
     * Constructor that initializes the StartView by loading the corresponding FXML file
     * and applying the relevant stylesheet.
     *
     * @throws IOException if there is an error loading the FXML file or the stylesheet.
     */
    public StartView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.START_VIEW));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Path.STYLE_START_VIEW); // Adds custom stylesheet
        this.setScene(scene);
        this.show();
    }

    /**
     * Singleton method that returns the single instance of the StartView.
     * If the instance does not exist, it creates a new one.
     *
     * @return The singleton instance of the StartView.
     * @throws IOException if there is an error initializing the view.
     */
    public static StartView getInstance() throws IOException {
        if (StartViewHolder.INSTANCE == null) {
            return StartViewHolder.INSTANCE = new StartView();
        } else {
            return StartViewHolder.INSTANCE;
        }
    }

    /**
     * Singleton instance holder to ensure only one StartView instance exists at a time.
     */
    private static class StartViewHolder {
        private static StartView INSTANCE;
    }

    /**
     * Deletes the current instance of StartView and closes the window.
     */
    public static void deleteInstance() {
        StartViewHolder.INSTANCE.close();
        StartViewHolder.INSTANCE = null;
    }
}

