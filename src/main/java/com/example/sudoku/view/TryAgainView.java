package com.example.sudoku.view;

import com.example.sudoku.controller.TryAgainController;
import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class represents the "Try Again" view in the Sudoku game.
 * It displays the screen shown to the player when the game ends, whether they won or lost.
 * The view is created and displayed by loading the corresponding FXML file.
 * It follows the Singleton design pattern to ensure only one instance of the view exists at a time.
 * @author Juan David Lopez
 * @author Grosman Klein Valencia
 * @version 1.0
 */
public class TryAgainView extends Stage {
    private TryAgainController tryAgainController;

    /**
     * Constructor that initializes the TryAgainView by loading the corresponding FXML file
     * and setting up the scene and controller for this view.
     *
     * @throws IOException if there is an error loading the FXML file or setting up the scene.
     */
    public TryAgainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.TRY_AGAIN_VIEW));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        this.tryAgainController = loader.getController();
        this.setScene(scene);
        this.show();
    }

    /**
     * Singleton instance holder class to ensure that only one TryAgainView instance exists at a time.
     */
    private static class TryAgainViewHolder {
        private static TryAgainView INSTANCE;
    }

    /**
     * Returns the singleton instance of the TryAgainView.
     * If the instance does not exist, a new one is created.
     *
     * @return The singleton instance of TryAgainView.
     * @throws IOException if there is an error initializing the view.
     */
    public static TryAgainView getInstance() throws IOException {
        // If instance is not created, instantiate it
        TryAgainView.TryAgainViewHolder.INSTANCE = TryAgainView.TryAgainViewHolder.INSTANCE != null
                ? TryAgainView.TryAgainViewHolder.INSTANCE
                : new TryAgainView();
        return TryAgainView.TryAgainViewHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of TryAgainView, closing the current window.
     */
    public static void deleteInstance() {
        TryAgainView.TryAgainViewHolder.INSTANCE.close();
        TryAgainView.TryAgainViewHolder.INSTANCE = null;
    }

    /**
     * Returns the controller associated with this TryAgainView.
     *
     * @return The TryAgainController instance for this view.
     */
    public TryAgainController getController() {
        return this.tryAgainController;
    }
}

