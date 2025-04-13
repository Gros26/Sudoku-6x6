package com.example.sudoku.controller;

import com.example.sudoku.view.StartView;
import com.example.sudoku.view.TryAgainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.io.IOException;

public class TryAgainController {

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonTryAgain;

    @FXML
    private Label txtGameMessage;

    @FXML
    private Label txtStateGame;

    @FXML
    void btnExit(ActionEvent event) throws IOException {
        TryAgainView.deleteInstance();
    }

    @FXML
    void btnTryAgain(ActionEvent event) throws IOException {
        StartView.getInstance();
        TryAgainView.deleteInstance();
    }


    public void setGameResult(boolean won) {
        if(won) {
            showWinMessage();
        }
        else {
            showLoseMessage();
        }
    }

    private void showWinMessage() {
        txtStateGame.setText("¡GANASTE!");
        txtStateGame.getStyleClass().clear();
        txtStateGame.getStyleClass().add("win-title");
        txtGameMessage.setText("Has logrado completar el nivel, juega nuevamente.");
    }

    public void showLoseMessage() {
        txtStateGame.setText("¡PERDISTE!");
        txtStateGame.getStyleClass().clear();
        txtStateGame.getStyleClass().add("lose-title");
        txtGameMessage.setText("No has completado el nivel.\nJuega nuevamente, no te rindas.");
    }



}
