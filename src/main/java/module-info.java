module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens com.example.sudoku.controller to javafx.fxml;
    exports com.example.sudoku.controller to javafx.fxml;

    opens com.example.sudoku to javafx.fxml;
    exports com.example.sudoku;


}