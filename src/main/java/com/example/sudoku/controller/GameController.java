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
        this.sudoku = new Sudoku(6,2,3,10);
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
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if(sudoku.getRows().get(i).get(j) == 0) {
                    gridPane.add(new TextField(""),i,j);
                }
                else {
                    gridPane.add(new TextField(sudoku.getRows().get(i).get(j).toString()), i, j);
                }

            }
        }
    }

}
