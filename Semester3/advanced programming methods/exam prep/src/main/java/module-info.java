module com.example.a7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.a7 to javafx.fxml;
    exports com.example.a7;
}