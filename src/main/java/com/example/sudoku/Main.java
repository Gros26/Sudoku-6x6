package com.example.sudoku;

import com.example.sudoku.utils.Path;
import com.example.sudoku.view.StartView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartView.getInstance();
    }

    /*
    Falta
    1. Que se muestre erorr algo rojo o asi cuando digite mal
    2. Cada bloque se debe llenar con dos numero
    3. El boton de ayuda
    4.
     */

}
