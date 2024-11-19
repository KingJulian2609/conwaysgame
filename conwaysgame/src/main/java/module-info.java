module com.example.conwaysgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.conwaysgame to javafx.fxml;
    exports com.example.conwaysgame;
}