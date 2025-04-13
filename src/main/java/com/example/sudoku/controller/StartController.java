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
import java.io.*;
import java.util.ArrayList;

public class StartController {
    private Sudoku sudoku;

    @FXML
    private Button ButtonContinue;

    @FXML
    void startGame(ActionEvent event) throws IOException {
        GameView.getInstance().getGameController().setGame();
        StartView.deleteInstance();
    }

    @FXML
    public void initialize() {
        File savedGame = new File("SudokuStatus.ser");
        ButtonContinue.setDisable(!savedGame.exists());
    }


    @FXML
    void btnLoadGame(ActionEvent event) throws IOException, ClassNotFoundException {
        loadGame();
    }

    private void loadGame() throws IOException, ClassNotFoundException {
        sudoku = null;
        FileInputStream fileIn = new FileInputStream("SudokuStatus.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        sudoku = (Sudoku) in.readObject();
        ArrayList<ArrayList<Integer>> gameStatus = (ArrayList<ArrayList<Integer>>) in.readObject();
        ArrayList<ArrayList<Integer>> solution = (ArrayList<ArrayList<Integer>>) in.readObject();

        in.close();
        fileIn.close();

        GameView.getInstance().getGameController().loadSavedGame(sudoku, gameStatus, solution);
        StartView.deleteInstance();

    }
}




