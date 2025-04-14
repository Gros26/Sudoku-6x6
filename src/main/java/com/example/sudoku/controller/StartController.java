package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import com.example.sudoku.utils.Path;
import com.example.sudoku.view.GameView;
import com.example.sudoku.view.StartView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * The StartController class manages the behavior of the start view of the Sudoku application.
 * It handles starting a new game and loading a previously saved game.
 * @author Juan David Lopez
 * @author Grosman Klein Garcia Valencia
 * @version 1.0
 */
public class StartController {

    private Sudoku sudoku;

    @FXML
    private Button ButtonContinue;

    /**
     * Handles the action for starting a new game.
     * It loads the GameView and sets up the new game, then deletes the StartView.
     *
     * @param event The action event triggered by the user (e.g., clicking the start button).
     * @throws IOException if an error occurs while loading the game view.
     */
    @FXML
    void startGame(ActionEvent event) throws IOException {
        GameView.getInstance().getGameController().setGame();
        StartView.deleteInstance();
    }

    /**
     * This method initializes the start view.
     * It checks for the existence of a saved game file ("SudokuStatus.ser") and enables or disables the Continue button accordingly.
     */
    @FXML
    public void initialize() {
        File savedGame = new File("SudokuStatus.ser");
        // Disable the continue button if there is no saved game file.
        ButtonContinue.setDisable(!savedGame.exists());
    }

    /**
     * Handles the action for loading a saved game.
     * It reads the saved game data from a file and then loads the game view with the saved game state.
     *
     * @param event The action event triggered by clicking the load game button.
     * @throws IOException if there is an error reading from the saved game file.
     * @throws ClassNotFoundException if the game data classes are not found during deserialization.
     */
    @FXML
    void btnLoadGame(ActionEvent event) throws IOException, ClassNotFoundException {
        loadGame();
    }

    /**
     * Loads the saved game data from the "SudokuStatus.ser" file.
     * It deserializes the game model, game status, and solution, then passes these to the GameView controller.
     *
     * @throws IOException if an error occurs while reading the saved game file.
     * @throws ClassNotFoundException if a class for an object serialized in the file cannot be found.
     */
    private void loadGame() throws IOException, ClassNotFoundException {
        sudoku = null;
        FileInputStream fileIn = new FileInputStream("SudokuStatus.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        sudoku = (Sudoku) in.readObject();
        ArrayList<ArrayList<Integer>> gameStatus = (ArrayList<ArrayList<Integer>>) in.readObject();
        ArrayList<ArrayList<Integer>> solution = (ArrayList<ArrayList<Integer>>) in.readObject();
        in.close();
        fileIn.close();

        // Load the saved game data into the GameView
        GameView.getInstance().getGameController().loadSavedGame(sudoku, gameStatus, solution);
        StartView.deleteInstance();
    }
}





