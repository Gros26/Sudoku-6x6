package com.example.sudoku.model;

import javafx.scene.control.Alert;

public interface IAlert {
    public void showError(String message, String headerText, String contentText);
    public void showWarning(String message, String headerText, String contentText);
    public void showInformation(String message, String headerText, String contentText);

}
