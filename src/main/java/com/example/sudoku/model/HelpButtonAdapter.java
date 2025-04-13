package com.example.sudoku.model;

import com.example.sudoku.view.GameView;
import com.example.sudoku.view.TryAgainView;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;

public class HelpButtonAdapter {

    private ArrayList<ArrayList<Integer>> gameStatus;
    private ArrayList<ArrayList<Integer>> solution;
    private GridPane gridPane;
    private int correctCellsCount = 0;
    private final int TOTAL_CELLS = 36; // Total de celdas en un Sudoku 6x6

    // Constructor
    public HelpButtonAdapter(ArrayList<ArrayList<Integer>> gameStatus, ArrayList<ArrayList<Integer>> solution, GridPane gridPane) {
        this.gameStatus = gameStatus;
        this.solution = solution;
        this.gridPane = gridPane;
    }

    // Método que maneja la acción del botón de ayuda
    public void showHelp() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                // Si la celda está vacía (valor 0)
                if (gameStatus.get(i).get(j) == 0) {
                    // Obtener el valor de la solución para esta celda
                    int solutionValue = solution.get(i).get(j);

                    // Actualiza el estado del juego
                    gameStatus.get(i).set(j, solutionValue);

                    // Busca el TextField existente en el GridPane
                    for (Node node : gridPane.getChildren()) {
                        if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                            // Asegúrate de que sea un TextField
                            if (node instanceof TextField) {
                                TextField txtField = (TextField) node;

                                // Actualiza el TextField con el valor de la solución
                                txtField.setText(String.valueOf(solutionValue));
                                txtField.setEditable(false); // Desactivar edición después de mostrar la ayuda

                                // Opcionalmente, puedes añadir una clase especial para indicar que es una ayuda
                                txtField.getStyleClass().add("grid-cell-hint");

                                // Solo ayudamos con una celda a la vez
                                return;
                            }
                        }
                    }
                }
            }
        }

        // Verificar si el jugador ha ganado después de mostrar la ayuda
        checkForWin();
    }

    // Verificar si todas las celdas están correctas
    private void checkForWin() {
        correctCellsCount = 0;

        // Contar cuántas celdas están correctas
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (gameStatus.get(i).get(j).equals(solution.get(i).get(j))) {
                    correctCellsCount++;
                }
            }
        }

        // Si todas las celdas están correctas, el jugador ha ganado
        if (correctCellsCount == TOTAL_CELLS) {
            // Lógica para indicar que el jugador ha ganado
            try {
                TryAgainView.getInstance().getController().setGameResult(true);
                GameView.deleteInstance();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

