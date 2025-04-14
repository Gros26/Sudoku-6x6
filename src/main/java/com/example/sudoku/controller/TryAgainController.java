package com.example.sudoku.controller;

import com.example.sudoku.view.StartView;
import com.example.sudoku.view.TryAgainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * The TryAgainController handles the logic for the Try Again view, including displaying
 * the game result (win/lose) and navigating the user to the start view or exiting the game.
 */
public class TryAgainController {

    @FXML
    private Button buttonExit; // Button to exit the game

    @FXML
    private Button buttonTryAgain; // Button to try again

    @FXML
    private Label txtGameMessage; // Label to display the game message

    @FXML
    private Label txtStateGame; // Label to display the game state (win or lose)

    /**
     * Handles the exit button action, closing the TryAgain view.
     *
     * @param event The event triggered by clicking the exit button.
     * @throws IOException If an I/O error occurs when deleting the TryAgain view instance.
     */
    @FXML
    void btnExit(ActionEvent event) throws IOException {
        TryAgainView.deleteInstance(); // Close the TryAgain view
    }

    /**
     * Handles the try again button action, restarting the game by navigating to the Start view.
     *
     * @param event The event triggered by clicking the try again button.
     * @throws IOException If an I/O error occurs when navigating to the Start view.
     */
    @FXML
    void btnTryAgain(ActionEvent event) throws IOException {
        StartView.getInstance(); // Open the Start view
        TryAgainView.deleteInstance(); // Close the TryAgain view
    }

    /**
     * Sets the game result message (win or lose) based on the provided boolean value.
     *
     * @param won A boolean indicating if the player won (true) or lost (false).
     */
    public void setGameResult(boolean won) {
        if(won) {
            showWinMessage(); // Show win message if the player won
        }
        else {
            showLoseMessage(); // Show lose message if the player lost
        }
    }

    /**
     * Displays the message for winning the game.
     */
    private void showWinMessage() {
        txtStateGame.setText("¡GANASTE!"); // Set the win message
        txtStateGame.getStyleClass().clear();
        txtStateGame.getStyleClass().add("win-title"); // Add style class for win
        txtGameMessage.setText("Has logrado completar el nivel, juega nuevamente."); // Set the additional win message
    }

    /**
     * Displays the message for losing the game.
     */
    public void showLoseMessage() {
        txtStateGame.setText("¡PERDISTE!"); // Set the lose message
        txtStateGame.getStyleClass().clear();
        txtStateGame.getStyleClass().add("lose-title"); // Add style class for lose
        txtGameMessage.setText("No has completado el nivel.\nJuega nuevamente, no te rindas."); // Set the additional lose message
    }
}

