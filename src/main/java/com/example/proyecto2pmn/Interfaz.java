package com.example.proyecto2pmn;

import javafx.scene.Node;
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
import java.util.Objects;

public class Interfaz extends Stage
{
    private Label titulo;
    private VBox vbox;
    public HBox hboxEcuacion;
    private TextField textFieldEcuacion;
    private LineChart<Number, Number> grafica;
    private Scene escena;
    private List<TextField> textFieldParametros;
    private List<HBox> hboxParametros;
    private Button buttonParametros;

    private void crearUI(Ecuacion metodo)
    {
        switch (metodo.titulo)
        {
            case "Gauss-Jordan":
                break;
            case "Gauss-Seidel":
                break;
            default:
                Label titulo = new Label("Método " + metodo.titulo);
                textFieldEcuacion = new TextField("Introduce la ecuación");
                Button buttonEcuacion = new Button("Ver gráfica");
                hboxEcuacion = new HBox(textFieldEcuacion, buttonEcuacion);
                vbox = new VBox(titulo, hboxEcuacion);


                buttonEcuacion.setOnAction(e -> {
                    metodo.añadirFuncion(textFieldEcuacion.getText());
                    if(vbox.getChildren().contains(grafica))
                    {
                        vbox.getChildren().remove(grafica);
                        vbox.getChildren().removeAll(textFieldParametros);
                        vbox.getChildren().remove(buttonParametros);
                        vbox.getChildren().removeAll(hboxParametros);
                        textFieldParametros.clear();
                        hboxParametros.clear();
                    }
                    grafica = metodo.graficarFuncion(-10, 10, .3);
                    vbox.getChildren().add(grafica);
                    obtenerParametros(metodo);
                });
                break;
        }


        escena = new Scene(vbox);
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
            hboxParametros.add(hbox);
        }
        buttonParametros = new Button("Usar parametros");
        buttonParametros.setOnAction(e ->
        {
            Double[] valoresIniciales = new Double[metodo.parametros.size()];
            for (int i = 0; i < textFieldParametros.size(); i++)
            {
                valoresIniciales[i] = Double.parseDouble(textFieldParametros.get(i).getText());
            }
            metodo.valoresParametro(valoresIniciales);
            metodo.calcularIteraciones();
            System.out.println(metodo.listaIteraciones);
            new Tabla(metodo);
        });
        vbox.getChildren().add(buttonParametros);
    }

    public Interfaz(Ecuacion metodo)
    {
        this.textFieldParametros = new ArrayList<>();
        this.hboxParametros = new ArrayList<>();
        crearUI(metodo);
        this.setScene(escena);
        this.setTitle("Interfaz para " + metodo.titulo);
        this.setMaximized(true);
        this.show();
    }
}
