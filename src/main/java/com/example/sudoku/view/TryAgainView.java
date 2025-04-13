package com.example.sudoku.view;

import com.example.sudoku.controller.TryAgainController;
import com.example.sudoku.utils.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TryAgainView extends Stage {
    private TryAgainController tryAgainController;

    public TryAgainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.TRY_AGAIN_VIEW));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        this.tryAgainController = loader.getController();
        this.setScene(scene);
        this.show();
    }

    private static class TryAgainViewHolder {
        private static TryAgainView INSTANCE;
    }

    public static TryAgainView getInstance() throws IOException {
        TryAgainView.TryAgainViewHolder.INSTANCE = TryAgainView.TryAgainViewHolder.INSTANCE != null ? TryAgainView.TryAgainViewHolder.INSTANCE : new TryAgainView();
        return TryAgainView.TryAgainViewHolder.INSTANCE;
    }

    public static void deleteInstance() {
        TryAgainView.TryAgainViewHolder.INSTANCE.close();
        TryAgainView.TryAgainViewHolder.INSTANCE = null;
    }

    public TryAgainController getController() {
        return this.tryAgainController;
    }



}
