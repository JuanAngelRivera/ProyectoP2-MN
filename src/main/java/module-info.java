module com.example.proyecto2pmn
{
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.proyecto2pmn to javafx.fxml;
    exports com.example.proyecto2pmn;
}