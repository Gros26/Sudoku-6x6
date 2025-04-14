package com.example.sudoku.model;

import javafx.scene.control.Alert;

/**
 * The AlertHelper class provides methods for displaying different types of alerts (error, warning, information)
 * using JavaFX's Alert dialog. It implements the IAlert interface to provide custom alert functionality.
 * @author Juan David Lopez
 * @version 1.0
 */
public class AlertHelper implements IAlert {

    /**
     * Displays an error alert with the provided message, header, and content text.
     *
     * @param message The message to be displayed as the alert title.
     * @param headerText The header text to be displayed in the alert.
     * @param contentText The content text to be displayed in the alert.
     */
    @Override
    public void showError(String message, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(message);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert with the provided message, header, and content text.
     *
     * @param message The message to be displayed as the alert title.
     * @param headerText The header text to be displayed in the alert.
     * @param contentText The content text to be displayed in the alert.
     */
    @Override
    public void showWarning(String message, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(message);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Displays an informational alert with the provided message, header, and content text.
     *
     * @param message The message to be displayed as the alert title.
     * @param headerText The header text to be displayed in the alert.
     * @param contentText The content text to be displayed in the alert.
     */
    @Override
    public void showInformation(String message, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(message);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

