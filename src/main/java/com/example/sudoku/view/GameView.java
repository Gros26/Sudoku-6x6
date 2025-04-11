package com.example.sudoku.view;

import com.example.sudoku.controller.GameController;
import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {
    private GameController gameController;


    public GameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.GAME_VIEW));

        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        this.gameController = loader.getController();
        this.setScene(scene);
        this.show();
    }

    public static GameView getInstance() throws IOException {
        if(GameView.GameViewHolder.INSTANCE == null) {
            return GameView.GameViewHolder.INSTANCE = new GameView();
        } else {
            return GameView.GameViewHolder.INSTANCE;
        }
    }

    private static class GameViewHolder {
        private static GameView INSTANCE;
    }

    public static void deleteInstance(){
        GameView.GameViewHolder.INSTANCE.close();
        GameView.GameViewHolder.INSTANCE = null;
    }


    public GameController getGameController() {
        return this.gameController;
    }



    
}
