package com.example.sudoku.model;

import javafx.scene.control.Alert;

/**
 * Interface that defines methods for displaying different types of alerts (error, warning, information).
 * Implementing classes will be responsible for the actual implementation of these alert methods.
 *
 * <p>This interface is meant to standardize the way alerts are displayed throughout the application,
 * ensuring that alerts are handled consistently and appropriately across different components of the system.</p>
 */
public interface IAlert {

    /**
     * Displays an error alert with a specified message, header text, and content text.
     *
     * @param message The message to display in the alert dialog box.
     * @param headerText The header text to display at the top of the alert box.
     * @param contentText The content text to display in the main body of the alert box.
     */
    public void showError(String message, String headerText, String contentText);

    /**
     * Displays a warning alert with a specified message, header text, and content text.
     *
     * @param message The message to display in the alert dialog box.
     * @param headerText The header text to display at the top of the alert box.
     * @param contentText The content text to display in the main body of the alert box.
     */
    public void showWarning(String message, String headerText, String contentText);

    /**
     * Displays an information alert with a specified message, header text, and content text.
     *
     * @param message The message to display in the alert dialog box.
     * @param headerText The header text to display at the top of the alert box.
     * @param contentText The content text to display in the main body of the alert box.
     */
    public void showInformation(String message, String headerText, String contentText);
}
