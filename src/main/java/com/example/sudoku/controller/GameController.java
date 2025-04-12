package com.example.sudoku.controller;

import com.example.sudoku.model.AlertHelper;
import com.example.sudoku.model.Sudoku;
import com.example.sudoku.view.GameView;
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

public class GameController {
    private Sudoku sudoku;
    private ArrayList<ArrayList<Integer>> gameStatus;
    private ArrayList<ArrayList<Integer>> solution;
    private AlertHelper alertHelper;

    @FXML
    private Button ButtonExit;

    @FXML
    private Button HelpButton;

    @FXML
    private GridPane gridPane;



    public GameController() {

    }

    @FXML
    public void initialize() {
        //inicializa el Sudoku solo una vez cuando se carga la vista
        this.sudoku = new Sudoku(6, 2, 3, 10);
        this.alertHelper = new AlertHelper();

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

    public void loadSavedGame(Sudoku sudoku, ArrayList<ArrayList<Integer>> gameStatus,
                              ArrayList<ArrayList<Integer>> solution) {
        this.sudoku = sudoku;
        this.gameStatus = gameStatus;
        this.solution = solution;
        this.alertHelper = new AlertHelper();

        setGame();
    }

    public void setGame() {
        // Limpia el grid primero
        gridPane.getChildren().clear();

        // Aplica estilos al GridPane
        gridPane.getStyleClass().add("sudoku-grid");
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        // Recorre cada celda del Sudoku y agrega un TextField
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txtField = new TextField();
                Integer value = gameStatus.get(i).get(j);
                int finalI = i;
                int finalJ = j;

                // Añade la clase base para todas las celdas
                txtField.getStyleClass().add("grid-cell");

                // Añade clases de estilo para los bordes de bloques
                // Determina qué tipo de borde necesita esta celda basado en su posición

                // Bordes de bloques superiores (filas 0 y 2)
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
                // Bordes de bloques inferiores (filas 1 y 3)
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
                // Bordes izquierdos que no son esquinas
                if ((j == 0 || j == 3) && !(i == 0 || i == 2 || i == 4 || i == 1 || i == 3 || i == 5)) {
                    txtField.getStyleClass().add("block-left");
                }
                // Bordes derechos que no son esquinas
                if ((j == 2 || j == 5) && !(i == 0 || i == 2 || i == 4 || i == 1 || i == 3 || i == 5)) {
                    txtField.getStyleClass().add("block-right");
                }

                if (value != 0) {
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
                                        // Añade clase para celda correcta
                                        txtField.getStyleClass().remove("grid-cell-incorrect");
                                        txtField.getStyleClass().add("grid-cell-correct");
                                    } else {
                                        // Número incorrecto
                                        txtField.setText("");
                                        txtField.getStyleClass().remove("grid-cell-correct");
                                        txtField.getStyleClass().add("grid-cell-incorrect");
                                        // Remueve la clase después de un momento
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
                                    }
                                } else {
                                    // No es un número del 1 al 6
                                    txtField.setText("");
                                    alertHelper.showWarning("Caracter incorrecto","Caracter incorrecto","Solo se permite el ingreso de digitos del 1 al 6");
                                }
                            }
                        }
                    });
                }

                // Ajusta el tamaño
                txtField.setPrefWidth(Double.MAX_VALUE);
                txtField.setPrefHeight(Double.MAX_VALUE);
                GridPane.setHgrow(txtField, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(txtField, javafx.scene.layout.Priority.ALWAYS);

                // Agregar el TextField a la celda correspondiente en el GridPane
                gridPane.add(txtField, j, i); // j es columna, i es fila
            }
        }
    }

    @FXML
    void btnExit(ActionEvent event) throws IOException {
        saveGame();
        GameView.deleteInstance();
    }

    @FXML
    void btnHelp(ActionEvent event) {

    }

    private void saveGame() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("SudokuStatus.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(sudoku);
        out.writeObject(gameStatus);
        out.writeObject(solution);
        out.close();
        fileOut.close();

        System.out.println("Objeto guardado con exito");
    }
}


