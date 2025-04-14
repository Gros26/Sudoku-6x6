package com.example.sudoku.model;

import com.example.sudoku.view.GameView;
import com.example.sudoku.view.TryAgainView;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The HelpButtonAdapter class is responsible for handling the logic when the help button is clicked in the Sudoku game.
 * It provides assistance by filling in the empty cells with the correct solution values and checks if the player has won the game.
 */
public class HelpButtonAdapter {

    private ArrayList<ArrayList<Integer>> gameStatus;
    private ArrayList<ArrayList<Integer>> solution;
    private GridPane gridPane;
    private int correctCellsCount = 0;
    private final int TOTAL_CELLS = 36; // Total of cells in a 6x6 Sudoku grid

    /**
     * Constructs a new HelpButtonAdapter object with the given game status, solution, and grid pane.
     *
     * @param gameStatus The current state of the game board (the values the user has entered).
     * @param solution The correct solution for the Sudoku puzzle.
     * @param gridPane The grid pane that contains the cells (TextFields).
     */
    public HelpButtonAdapter(ArrayList<ArrayList<Integer>> gameStatus, ArrayList<ArrayList<Integer>> solution, GridPane gridPane) {
        this.gameStatus = gameStatus;
        this.solution = solution;
        this.gridPane = gridPane;
    }

    /**
     * Handles the action when the help button is clicked.
     * It fills in empty cells (value 0) with the correct solution value and updates the game status and grid.
     */
    public void showHelp() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                // If the cell is empty (value 0)
                if (gameStatus.get(i).get(j) == 0) {
                    // Get the solution value for this cell
                    int solutionValue = solution.get(i).get(j);

                    // Update the game status
                    gameStatus.get(i).set(j, solutionValue);

                    // Find the corresponding TextField in the GridPane
                    for (Node node : gridPane.getChildren()) {
                        if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                            // Ensure it is a TextField
                            if (node instanceof TextField) {
                                TextField txtField = (TextField) node;

                                // Update the TextField with the solution value
                                txtField.setText(String.valueOf(solutionValue));
                                txtField.setEditable(false); // Disable editing after showing the help

                                // Optionally, add a class to indicate that this cell has been filled with help
                                txtField.getStyleClass().add("grid-cell-hint");

                                // Only provide help for one cell at a time
                                return;
                            }
                        }
                    }
                }
            }
        }

        // Check if the player has won after showing the help
        checkForWin();
    }

    /**
     * Checks if the player has won the game by comparing the game status with the solution.
     * If all cells are correct, the game is considered won.
     */
    private void checkForWin() {
        correctCellsCount = 0;

        // Count how many cells are correct
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameStatus.get(i).get(j).equals(solution.get(i).get(j))) {
                    correctCellsCount++;
                }
            }
        }

        // If all cells are correct, the player has won
        if (correctCellsCount == TOTAL_CELLS) {
            // Logic to indicate that the player has won
            try {
                TryAgainView.getInstance().getController().setGameResult(true);
                GameView.deleteInstance();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


