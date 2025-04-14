package com.example.sudoku.utils;

/**
 * Utility class that contains the paths to the various resources used in the Sudoku application.
 * These paths include the locations of FXML files, CSS files, and other resources required
 * to load views and stylesheets in the application.
 *
 * <p>Note: The constants in this class point to the resources, and it is recommended to replace
 * the static string constants with an {@link Enum} for better type safety and organization in the future.</p>
 * @author Juan David Lopez
 * @author Grosman Klein Valencia
 * @version 3.0
 */
public class Path {

    /**
     * The path to the StartView FXML file, which is the main screen where the user starts the game.
     */
    public static final String START_VIEW = "/com/example/sudoku/StartView.fxml";

    /**
     * The path to the GameView FXML file, which represents the main game screen where the Sudoku puzzle is played.
     */
    public static final String GAME_VIEW = "/com/example/sudoku/GameView.fxml";

    /**
     * The path to the stylesheet for the StartView, used for styling the main screen of the game.
     */
    public static final String STYLE_START_VIEW = Path.class.getResource("/com/example/sudoku/StartStyle.css").toExternalForm();

    /**
     * The path to the TryAgainView FXML file, which is shown when the player finishes the game,
     * either to retry or view the results.
     */
    public static final String TRY_AGAIN_VIEW = "/com/example/sudoku/TryAgainView.fxml";
}

