package com.example.sudoku.controller;

import com.example.sudoku.model.AlertHelper;
import com.example.sudoku.model.HelpButtonAdapter;
import com.example.sudoku.model.Sudoku;
import com.example.sudoku.view.GameView;
import com.example.sudoku.view.TryAgainView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The GameController class is responsible for managing the gameplay logic of the Sudoku application.
 * <p>
 * It handles initializing the Sudoku model, setting up the game grid, processing user input,
 * validating moves, handling game outcomes, and saving the game state.
 * </p>
 * @author Juan David Lopez
 * @author Grosman Klein Valencia
 * @version 2.0
 */
public class GameController {
    private Sudoku sudoku;
    private ArrayList<ArrayList<Integer>> gameStatus;
    private ArrayList<ArrayList<Integer>> solution;
    private AlertHelper alertHelper;
    private int correctCellsCount = 0;
    private int incorrectCellsCount = 0;
    private final int TOTAL_CELLS = 36;

    @FXML
    private Button ButtonExit;

    @FXML
    private Button HelpButton;

    @FXML
    private GridPane gridPane;

    /**
     * Default constructor for GameController.
     */
    public GameController() {
        // Empty constructor; initialization is done in the initialize() method.
    }

    /**
     * Initializes the game by creating a new Sudoku instance with a fixed distribution,
     * preparing the solution and game state arrays, and populating the grid with TextFields.
     */
    @FXML
    public void initialize() {
        // Initializes the Sudoku only once when the view loads.
        this.sudoku = new Sudoku(6, 2, 3, true);
        this.alertHelper = new AlertHelper();

        // Obtain the complete solution.
        sudoku.copy_original_board_into_temp();
        sudoku.solve();

        // Create copies of the matrices for solution and game status.
        this.solution = new ArrayList<>();
        for (ArrayList<Integer> row : sudoku.getTemp_rows()) {
            ArrayList<Integer> newRow = new ArrayList<>(row);
            this.solution.add(newRow);
        }

        this.gameStatus = new ArrayList<>();
        for (ArrayList<Integer> row : sudoku.getRows()) {
            ArrayList<Integer> newRow = new ArrayList<>(row);
            this.gameStatus.add(newRow);
        }

        setGame();
    }

    /**
     * Loads a saved game by deserializing the Sudoku, game status, and solution,
     * and then reinitializes the game grid.
     *
     * @param sudoku     The saved Sudoku instance.
     * @param gameStatus The saved game state as a 2D ArrayList of Integers.
     * @param solution   The saved solution as a 2D ArrayList of Integers.
     */
    public void loadSavedGame(Sudoku sudoku, ArrayList<ArrayList<Integer>> gameStatus,
                              ArrayList<ArrayList<Integer>> solution) {
        this.sudoku = sudoku;
        this.gameStatus = gameStatus;
        this.solution = solution;
        this.alertHelper = new AlertHelper();

        setGame();
    }

    /**
     * Populates the GridPane with TextFields representing each cell of the Sudoku board.
     * <p>
     * Each cell is assigned a style based on its position for visual block borders.
     * If the cell value is non-zero, it is displayed and made non-editable; otherwise,
     * an input listener is added to validate user input.
     * </p>
     */
    public void setGame() {
        // Clear the grid first.
        gridPane.getChildren().clear();

        // Apply styles to the GridPane.
        gridPane.getStyleClass().add("sudoku-grid");
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        // Iterate over each cell in the Sudoku grid and add a TextField.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txtField = new TextField();
                Integer value = gameStatus.get(i).get(j);
                int finalI = i;
                int finalJ = j;

                // Add the base style class for all cells.
                txtField.getStyleClass().add("grid-cell");

                // Add style classes for cell borders based on block position.
                // Top block borders (rows 0, 2, 4)
                if (i == 0 || i == 2 || i == 4) {
                    if (j == 0) {
                        txtField.getStyleClass().add("block-top-left");
                    } else if (j == 3) {
                        txtField.getStyleClass().add("block-top-left");
                    } else if (j == 5) {
                        txtField.getStyleClass().add("block-top-right");
                    } else if (j == 2) {
                        txtField.getStyleClass().add("block-top-right");
                    } else {
                        txtField.getStyleClass().add("block-top");
                    }
                }
                // Bottom block borders (rows 1, 3, 5)
                else if (i == 1 || i == 3 || i == 5) {
                    if (j == 0) {
                        txtField.getStyleClass().add("block-bottom-left");
                    } else if (j == 3) {
                        txtField.getStyleClass().add("block-bottom-left");
                    } else if (j == 5) {
                        txtField.getStyleClass().add("block-bottom-right");
                    } else if (j == 2) {
                        txtField.getStyleClass().add("block-bottom-right");
                    } else {
                        txtField.getStyleClass().add("block-bottom");
                    }
                }
                // Left non-corner borders.
                if ((j == 0 || j == 3) && !(i == 0 || i == 2 || i == 4 || i == 1 || i == 3 || i == 5)) {
                    txtField.getStyleClass().add("block-left");
                }
                // Right non-corner borders.
                if ((j == 2 || j == 5) && !(i == 0 || i == 2 || i == 4 || i == 1 || i == 3 || i == 5)) {
                    txtField.getStyleClass().add("block-right");
                }

                // If the cell value is not zero, display it and disable editing.
                if (value != 0) {
                    txtField.setText(value.toString());
                    txtField.setEditable(false);
                } else {
                    // Otherwise, add a listener to validate input.
                    txtField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> textObservable, String previousText, String newValue) {
                            if (!newValue.isEmpty()) {
                                if (newValue.matches("[1-6]")) {
                                    // Validate against the saved solution.
                                    if (Integer.parseInt(newValue) == solution.get(finalI).get(finalJ)) {
                                        // Update game status.
                                        gameStatus.get(finalI).set(finalJ, Integer.parseInt(newValue));
                                        txtField.setEditable(false);
                                        // Update cell style for a correct answer.
                                        txtField.getStyleClass().remove("grid-cell-incorrect");
                                        txtField.getStyleClass().add("grid-cell-correct");

                                        correctCellsCount++;
                                        if (correctCellsCount == TOTAL_CELLS) {
                                            try {
                                                // Player has won the game.
                                                TryAgainView.getInstance().getController().setGameResult(true);
                                                GameView.deleteInstance();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    } else {
                                        // Incorrect number: clear input and add error style.
                                        txtField.setText("");
                                        txtField.getStyleClass().remove("grid-cell-correct");
                                        txtField.getStyleClass().add("grid-cell-incorrect");
                                        // Remove error style after a short delay.
                                        new java.util.Timer().schedule(
                                                new java.util.TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        javafx.application.Platform.runLater(() -> {
                                                            txtField.getStyleClass().remove("grid-cell-incorrect");
                                                        });
                                                    }
                                                },
                                                1000
                                        );
                                        incorrectCellsCount++;
                                        if (incorrectCellsCount == 3) {
                                            // Player has lost the game.
                                            try {
                                                TryAgainView.getInstance().getController().setGameResult(false);
                                                GameView.deleteInstance();
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                } else {
                                    // Input is not a valid digit (1-6): clear the field and show warning.
                                    txtField.setText("");
                                    alertHelper.showWarning("Caracter incorrecto", "Caracter incorrecto", "Solo se permite el ingreso de digitos del 1 al 6");
                                }
                            }
                        }
                    });
                }

                // Configure the TextField to expand and fill the cell.
                txtField.setPrefWidth(Double.MAX_VALUE);
                txtField.setPrefHeight(Double.MAX_VALUE);
                GridPane.setHgrow(txtField, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(txtField, javafx.scene.layout.Priority.ALWAYS);

                // Add the TextField to the GridPane at the specified column (j) and row (i).
                gridPane.add(txtField, j, i);
            }
        }
    }

    /**
     * Handles the exit button action by saving the current game state and closing the game view.
     *
     * @param event The action event triggered by clicking the exit button.
     * @throws IOException if an error occurs during the game state saving process.
     */
    @FXML
    void btnExit(ActionEvent event) throws IOException {
        saveGame();
    }

    /**
     * Handles the help button action by using a HelpButtonAdapter to fill in the empty cells of the Sudoku board
     * with the solution and check for a win condition.
     *
     * @param event The action event triggered by clicking the help button.
     * @throws IOException if an error occurs during the help action.
     */
    @FXML
    void btnHelp(ActionEvent event) throws IOException {
        // Use the adapter to show the solution for empty cells and verify win condition.
        HelpButtonAdapter helpAdapter = new HelpButtonAdapter(gameStatus, solution, gridPane);
        helpAdapter.showHelp();
    }

    /**
     * Saves the current game state to a file named "SudokuStatus.ser" by serializing the Sudoku model,
     * the game status, and the solution. After saving, the game view is closed.
     *
     * @throws IOException if an I/O error occurs during saving.
     */
    private void saveGame() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("SudokuStatus.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(sudoku);
        out.writeObject(gameStatus);
        out.writeObject(solution);
        out.close();
        fileOut.close();
        GameView.deleteInstance();
    }
}



