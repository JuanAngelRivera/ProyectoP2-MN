package com.example.proyecto2pmn;

import com.example.proyecto2pmn.NRmultivariable.Algoritmo;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interfaz extends Stage
{
    private Label titulo, descripcion;
    private VBox vbox;
    private HBox hboxDescripcion;
    private LineChart<Number, Number> grafica;
    private Scene escena;
    private List<TextField> textFieldParametros;
    private List<HBox> hboxParametros;
    private Button buttonParametros;

    private void crearUI(Ecuacion metodo)
    {
        titulo = new Label("Método " + metodo.titulo);
        descripcion = new Label(metodo.descripcion);
        descripcion.setWrapText(true);
        ImageView imagen = new ImageView(metodo.imagen);
        imagen.setFitHeight(100);
        imagen.setFitWidth(100);
        hboxDescripcion = new HBox(descripcion, imagen);
        vbox = new VBox(titulo, hboxDescripcion);
        switch (metodo.titulo)
        {
            case "Gauss-Jordan":
                GaussJordanUI((com.example.proyecto2pmn.GaussJordan.Algoritmo) metodo);
                break;
            case "Gauss-Seidel":
                GuassSeidelUI(metodo);
                break;
            case "Newton-Rhapson Multivariable":
                newtonRhapsonMultivariableUI((Algoritmo) metodo);
                break;
            default:
                newtonRhapsonUI(metodo);
                break;
        }
        escena = new Scene(vbox);
    }

    private void GuassSeidelUI(Ecuacion metodo)
    {

    }

    private void GaussJordanUI(com.example.proyecto2pmn.GaussJordan.Algoritmo metodo)
    {
        TextField txtEcuaciones = new TextField("Introduce el número de ecuaciones");
        Button buttonEcuaciones = new Button("OK");
        vbox.getChildren().addAll(txtEcuaciones, buttonEcuaciones);
        buttonEcuaciones.setOnAction(e -> {
            TextField txtVariables = new TextField("Introduce el numero de variables");
            Button buttonVariables = new Button("OK");
            vbox.getChildren().addAll(txtVariables, buttonVariables);
            metodo.a_numbFunc = Integer.parseInt(txtEcuaciones.getText());
            buttonVariables.setOnAction(event -> {
                metodo.a_numbCoefficient = Integer.parseInt(txtVariables.getText());
                metodo.a_functions = new String[metodo.a_numbFunc];
                GridPane grid = metodo.crearGridPane();
                Button buttonParametros = new Button("Obtener solución");

                buttonParametros.setOnAction(event1 -> {
                    Map <Integer, StringBuilder> valores = new HashMap<>();

                    for(Node node : grid.getChildren())
                    {
                        if (node instanceof TextField)
                        {
                            TextField tf = (TextField) node;
                            Integer renglon = GridPane.getRowIndex(node);
                            Integer columna = GridPane.getColumnIndex(node);

                            renglon = (renglon == null) ? 0: renglon;
                            columna = (columna == null) ? 0: columna;

                            if(renglon == 0 || columna == 0)
                                continue;
                            if(columna == grid.getColumnCount() - 1)
                            {
                                System.out.println(columna);
                                String valor = tf.getText().trim();
                                valor = "=" + valor;
                                valores.putIfAbsent(renglon, new StringBuilder());
                                valores.get(renglon).append(valor);
                                continue;
                            }

                            String valor = tf.getText().trim();

                            if (!valor.isEmpty() && !valor.startsWith("-"))
                                valor = "+" + valor;

                            valores.putIfAbsent(renglon, new StringBuilder());
                            valores.get(renglon).append(valor + "x" + columna);
                        }
                    }
                    List<String> renglonesConcatenados = new ArrayList<>();
                    valores.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry ->
                            renglonesConcatenados.add(entry.getValue().toString()));
                    for (int i = 0; i < metodo.a_functions.length; i++)
                    {
                        metodo.a_functions[i] = renglonesConcatenados.get(i);
                    }
                    metodo.calcularIteraciones();
                    Label estado = new Label(metodo.estado);
                    vbox.getChildren().add(estado);
                    for (int i = 0; i < metodo.resultados.size(); i++)
                    {
                        Label label = new Label(metodo.resultados.get(i));
                        vbox.getChildren().add(label);
                    }
                });
                vbox.getChildren().addAll(grid, buttonParametros);
            });
        });
    }

    private void newtonRhapsonMultivariableUI(Algoritmo metodo)
    {
        TextField txtFxy1 = new TextField("Escriba la ecuación f1(x,y)");
        TextField txtFxy2 = new TextField("Escriba la ecuación f2(x,y)");
        Button buttonEcuaciones = new Button("Usar estas ecuaciones");
        vbox.getChildren().addAll(txtFxy1, txtFxy2, buttonEcuaciones);
        buttonEcuaciones.setOnAction(e -> {
            metodo.setFun(txtFxy1.getText(), txtFxy2.getText());

            Label f1dx = new Label("f1dx = " + metodo.fun1dx);
            Label f1dy = new Label("f1dy = " + metodo.fun1dy);
            Label f2dx = new Label("f2dx = " + metodo.fun2dx);
            Label f2dy = new Label("f2dy = " + metodo.fun2dy);
            vbox.getChildren().addAll(f1dx, f1dy, f2dx, f2dy);
            obtenerParametros(metodo);
        });

    }

    private void newtonRhapsonUI (Ecuacion metodo)
    {
        TextField textFieldEcuacion = new TextField("Introduce la ecuación");
        Button buttonEcuacion = new Button("Ver gráfica");
        HBox hboxEcuacion = new HBox(textFieldEcuacion, buttonEcuacion);
        vbox.getChildren().addAll(hboxEcuacion);


            buttonEcuacion.setOnAction(e -> {
                try
                {
                    boolean a = metodo.añadirFuncion(textFieldEcuacion.getText());
                    if(a)
                    {
                        if (vbox.getChildren().contains(grafica))
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
                    }
                    else
                        new VentanaError("No se pudo graficar la función");
                }
                catch (Exception ex)
                {
                    new VentanaError(ex.getMessage());
                }
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
            hboxParametros.add(hbox);
        }
        buttonParametros = new Button("Usar parametros");
        buttonParametros.setOnAction(e ->
        {
            try
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
            }
            catch (Exception ex)
            {
                new VentanaError("Introduce solo valores numéricos");
            }
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
