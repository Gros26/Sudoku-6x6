package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameController {
    private Sudoku sudoku;

    @FXML
    private GridPane gridPane;

    public GameController() {
        this.sudoku = new Sudoku(6, 2, 3, 10); // Inicializa el Sudoku con 10 elementos aleatorios
    }

    @FXML
    public void initialize() {
        // Ya puedes acceder a gridPane aquí
        System.out.println("Columnas: " + gridPane.getColumnConstraints().size());
        System.out.println("Filas: " + gridPane.getRowConstraints().size());

        // Ejemplo: recorrer todos los nodos (si ya los agregaste desde el FXML o por código)
        for (Node node : gridPane.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row == null) row = 0;
            if (col == null) col = 0;
            System.out.println("Nodo en fila " + row + ", columna " + col);
        }
    }

    public void setGame() {
        // Recorre cada celda del Sudoku y agrega un TextField
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                // Obtener el valor de la celda en el Sudoku
                int cellValue = sudoku.getRows().get(i).get(j);

                // Crear el TextField
                TextField textField = new TextField();

                // Si el valor es diferente de 0, establecerlo como el valor del TextField
                if (cellValue != 0) {
                    textField.setText(Integer.toString(cellValue));
                    textField.setEditable(false);  // Deshabilitar la edición del TextField
                } else {
                    textField.setText("");  // Si el valor es 0, dejar la celda vacía
                }

                // Asegura que el TextField se ajuste al tamaño de la celda del GridPane
                textField.setPrefWidth(Double.MAX_VALUE); // Hace que el ancho del TextField ocupe todo el espacio disponible
                textField.setPrefHeight(Double.MAX_VALUE); // Hace que el alto del TextField ocupe todo el espacio disponible

                // Configura el ajuste de crecimiento para que los TextFields se expandan correctamente
                GridPane.setHgrow(textField, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(textField, javafx.scene.layout.Priority.ALWAYS);

                // Agregar el TextField a la celda correspondiente en el GridPane
                gridPane.add(textField, j, i); // Nota: j es la columna, i es la fila
                System.out.println("Añadiendo TextField en fila " + i + ", columna " + j);
            }
        }
    }
}


