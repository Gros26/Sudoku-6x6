package com.example.sudoku.view;

import com.example.sudoku.controller.GameController;
import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The {@link GameView} class represents the main game screen for the Sudoku application.
 * This view is displayed when the user starts a new game, showing the Sudoku grid and associated controls.
 * It follows the Singleton design pattern to ensure that only one instance of the GameView exists at any given time.
 * @author Juan David Lopez
 * @author Grosman Klein Valencia
 * @version 2.0
 */
public class GameView extends Stage {
    private GameController gameController;

    /**
     * Constructor that initializes the GameView by loading the corresponding FXML file,
     * applying the required stylesheet, and setting up the scene and controller for this view.
     *
     * @throws IOException if there is an error loading the FXML file or the stylesheet.
     */
    public GameView() throws IOException {
        // Load the FXML file for the game view
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.GAME_VIEW));

        // Load the root layout pane (AnchorPane)
        AnchorPane pane = loader.load();

        // Create a new scene using the loaded layout and applying the stylesheets
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/com/example/sudoku/GameStyle.css").toExternalForm());

        // Get the controller associated with the GameView
        this.gameController = loader.getController();

        // Set the scene and show the stage
        this.setScene(scene);
        this.show();
    }

    /**
     * Singleton method that returns the instance of the GameView.
     * If the instance does not exist, it creates a new one.
     *
     * @return The singleton instance of the GameView.
     * @throws IOException if there is an error initializing the view.
     */
    public static GameView getInstance() throws IOException {
        if (GameView.GameViewHolder.INSTANCE == null) {
            return GameView.GameViewHolder.INSTANCE = new GameView();
        } else {
            return GameView.GameViewHolder.INSTANCE;
        }
    }

    /**
     * Inner class that holds the singleton instance of GameView.
     * This class ensures that only one instance of the GameView exists at any time.
     */
    private static class GameViewHolder {
        private static GameView INSTANCE;
    }

    /**
     * Deletes the singleton instance of GameView, closing the window and cleaning up resources.
     */
    public static void deleteInstance() {
        GameView.GameViewHolder.INSTANCE.close();
        GameView.GameViewHolder.INSTANCE = null;
    }

    /**
     * Gets the GameController associated with this GameView.
     *
     * @return The GameController instance for this view.
     */
    public GameController getGameController() {
        return this.gameController;
    }
}
