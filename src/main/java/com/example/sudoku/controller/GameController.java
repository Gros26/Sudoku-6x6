package com.example.sudoku.controller;

import com.example.sudoku.model.Sudoku;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
                TextField txtField = new TextField();
                Integer value = sudoku.getRows().get(i).get(j);
                int finalI = i;
                int finalJ = j;
                if( value != 0) {
                    txtField.setText(value.toString());
                    txtField.setEditable(false);
                }
                else {
                    txtField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> textObservable, String previousText, String currentTex) {
                            //aqui verificamos que solo se puedan numeros del 1 al 6
                            if(currentTex.matches("[1-6]")) {
                                //aqui ya se verifica que el numero ingresado si sea correcto en esa posicion
                                if(value.equals(sudoku.getTemp_rows().get(finalI).get(finalJ))) {
                                    txtField.setText(currentTex);
                                    txtField.setEditable(false);
                                }
                                else {
                                    System.out.println("error, cambiar color de la casilla o algo");
                                    txtField.setText("");
                                }
                            }
                            else {
                                System.out.println("Letra incorrecta, por el momento asi");
                            }
                        }
                    });
                }
                gridPane.add(txtField,i,j);
            }
        }
    }

}
