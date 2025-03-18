module com.example.proyecto2pmn
{
    requires javafx.controls;
    requires javafx.fxml;
    requires jep;
    requires djep;
    requires commons.math3;
    requires jdk.unsupported.desktop;


    opens com.example.proyecto2pmn to javafx.fxml;
    exports com.example.proyecto2pmn;
    exports com.example.proyecto2pmn.newtonRhapson;
    opens com.example.proyecto2pmn.newtonRhapson to javafx.fxml;
}