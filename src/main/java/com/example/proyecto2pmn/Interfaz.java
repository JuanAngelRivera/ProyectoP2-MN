package com.example.proyecto2pmn;

import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interfaz extends Stage
{
    private Label titulo;
    private VBox vbox;
    private TextField textFieldEcuacion;
    private LineChart<Number, Number> grafica;
    private Button buttonEcuacion;
    private void crearUI()
    {

    }
    public Interfaz(Ecuacion metodo)
    {

        crearUI();
        this.setTitle("HOLA");
        this.setMaximized(true);
        this.show();
    }
}
