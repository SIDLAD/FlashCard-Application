module com.example.flashcard {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flashcard to javafx.fxml;
    exports com.example.flashcard;
}