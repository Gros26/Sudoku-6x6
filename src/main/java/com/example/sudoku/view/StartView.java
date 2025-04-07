package com.example.sudoku.view;

import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView extends Stage {

    public StartView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.START_VIEW));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Path.STYLE_START_VIEW);
        this.setScene(scene);
        this.show();
    }

    public static StartView getInstance() throws IOException {
        if (StartViewHolder.INSTANCE == null) {
            return StartViewHolder.INSTANCE = new StartView();
        } else {
            return StartViewHolder.INSTANCE;
        }
    }

    private static class StartViewHolder {
        private static StartView INSTANCE;
    }

    public static void deleteInstance(){
        StartViewHolder.INSTANCE.close();
        StartViewHolder.INSTANCE = null;
    }

}
