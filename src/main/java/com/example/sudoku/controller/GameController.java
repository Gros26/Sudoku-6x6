package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameController {
    private Sudoku sudoku;
    private ArrayList<ArrayList<Integer>> gameStatus;
    private ArrayList<ArrayList<Integer>> solution;
    @FXML
    private GridPane gridPane;


    public GameController() {

    }

    @FXML
    public void initialize() {
        //inicializa el Sudoku solo una vez cuando se carga la vista
        this.sudoku = new Sudoku(6, 2, 3, 10);

        // Obtén la solución completa
        sudoku.copy_original_board_into_temp();
        sudoku.solve();

        //copias de las matrices para duplicados o asi
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

    public void setGame() {
        //limpia el grid primero
        gridPane.getChildren().clear();

        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txtField = new TextField();
                Integer value = gameStatus.get(i).get(j);
                int finalI = i;
                int finalJ = j;

                if(value != 0) {
                    txtField.setText(value.toString());
                    txtField.setEditable(false);
                } else {

                    txtField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> textObservable, String previousText, String newValue) {
                            if (!newValue.isEmpty()) {
                                if (newValue.matches("[1-6]")) {
                                    // Verifica contra la solución guardada
                                    if (Integer.parseInt(newValue) == solution.get(finalI).get(finalJ)) {
                                        // Actualiza el estado del juego
                                        gameStatus.get(finalI).set(finalJ, Integer.parseInt(newValue));
                                        txtField.setEditable(false);
                                    } else {
                                        // Número incorrecto
                                        txtField.setText("");
                                    }
                                } else {
                                    // No es un número del 1 al 6
                                    txtField.setText("");
                                }
                            }
                        }
                    });
                }
                gridPane.add(txtField,j,i); // Nota: j es columna, i es fila en GridPane
            }
        }
    }
}
