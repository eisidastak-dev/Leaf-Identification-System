module com.example.ca1dsa2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ca1dsa2 to javafx.fxml;
    exports com.example.ca1dsa2;
}