module com.example.proyecto2pmn
{
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrap.core;

    opens com.example.proyecto2pmn to javafx.fxml;
    exports com.example.proyecto2pmn;
}