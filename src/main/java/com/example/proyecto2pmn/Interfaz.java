package com.example.proyecto2pmn;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Interfaz extends Stage
{
    private Label titulo;
    private VBox vbox;
    public HBox hboxEcuacion;
    private TextField textFieldEcuacion;
    private LineChart<Number, Number> grafica;
    private Button buttonEcuacion;
    private Scene escena;
    private List<TextField> textFieldParametros;

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
            obtenerParametros(metodo);
        });
    }

    public void obtenerParametros(Ecuacion metodo)
    {
        for (String parametro : metodo.parametros)
        {
            Label label = new Label(parametro);
            TextField tf = new TextField("Valor para " + parametro);
            HBox hbox = new HBox(label, tf);
            vbox.getChildren().add(hbox);
            textFieldParametros.add(tf);
        }
        Button button = new Button("Usar parametros");
        button.setOnAction(e ->
        {
            Double[] valoresIniciales = new Double[metodo.parametros.size()];
            for (int i = 0; i < textFieldParametros.size(); i++)
            {
                valoresIniciales[i] = Double.parseDouble(textFieldParametros.get(i).getText());
            }
            metodo.valoresParametro(valoresIniciales);
            metodo.calcularIteraciones();
            new Tabla(metodo);
        });
        vbox.getChildren().add(button);
    }

    public Interfaz(Ecuacion metodo)
    {
        this.textFieldParametros = new ArrayList<>();
        crearUI(metodo);
        this.setScene(escena);
        this.setTitle("HOLA");
        this.setMaximized(true);
        this.show();
    }
}
