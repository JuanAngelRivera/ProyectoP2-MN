package com.example.proyecto2pmn;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Interfaz extends Stage
{
    private Label titulo;
    private VBox vbox;
    public HBox hboxEcuacion;
    private TextField textFieldEcuacion;
    private LineChart<Number, Number> grafica;
    private Button buttonEcuacion;
    private Scene escena;
    private void crearUI(Ecuacion metodo)
    {
        Label titulo = new Label("Método " + metodo.titulo);

        textFieldEcuacion = new TextField("Introduce la ecuación");
        buttonEcuacion = new Button("Ver gráfica");
        hboxEcuacion = new HBox(textFieldEcuacion, buttonEcuacion);
        vbox = new VBox(titulo, hboxEcuacion);
        escena = new Scene(vbox);

        buttonEcuacion.setOnAction(e -> {
            metodo.añadirFuncion(textFieldEcuacion.getText());
            grafica = metodo.graficarFuncion(-10, 10, .3);
            vbox.getChildren().add(grafica);
        });
    }
    public Interfaz(Ecuacion metodo)
    {
        crearUI(metodo);
        this.setScene(escena);
        this.setTitle("HOLA");
        this.setMaximized(true);
        this.show();
    }
}
