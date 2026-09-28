module com.example.ca1dsa2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.compiler;
    requires java.desktop;


    opens com.example.ca1dsa2 to javafx.fxml;
    exports com.example.ca1dsa2;
    exports Controllers;
    opens Controllers to javafx.fxml;
}