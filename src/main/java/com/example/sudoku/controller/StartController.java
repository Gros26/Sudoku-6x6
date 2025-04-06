package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import com.example.sudoku.view.GameView;
import com.example.sudoku.view.StartView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    void startGame(ActionEvent event) throws IOException {
        GameView.getInstance().getGameController().setGame();
        StartView.deleteInstance();
    }

    @FXML
    public void initialize() {

    }




}




