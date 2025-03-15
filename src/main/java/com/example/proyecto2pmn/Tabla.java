package com.example.proyecto2pmn;

import com.example.proyecto2pmn.NRmultivariable.Algoritmo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Tabla extends Stage
{
    private Label titulo;
    private Ecuacion metodo;
    VBox vbox;
    Scene escena;
    TableView<Fila> tabla;
    ObservableList<Fila> datos;

    public void crearUI()
    {
        titulo = new Label("Tabla del metodo " + metodo.titulo);
        tabla = new TableView<>();
        vbox = new VBox(titulo, tabla);
        escena = new Scene(vbox);
    }

    private void configurarTabla()
    {
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (int i = 0; i < metodo.columnasTabla.size(); i++)
        {
            TableColumn<Fila, String> columna = new TableColumn<>(metodo.columnasTabla.get(i));
            final int index = i;
            columna.setCellValueFactory(cellData -> cellData.getValue().obtenerValores(index));
            tabla.getColumns().add(columna);
        }
    }

    public void cargarTabla(ArrayList<String[]> listaIteraciones)
    {
        List<Fila> filas = new ArrayList<>();
        for (int i = 0; i < listaIteraciones.size(); i++)
        {
            filas.add(new Fila(List.of(listaIteraciones.get(i))));
        }
        datos.setAll(filas);
        tabla.setItems(datos);
        vbox.getChildren().add(new Label("Raíz aproximada con error <= " + metodo.obtenerErrorU() + ":\n"));
        if (metodo instanceof Algoritmo)
        {
            Label x = new Label("Valor de x = " + ((Algoritmo) metodo).valorxi1());
            Label y = new Label("Valor de y = " + ((Algoritmo) metodo).valoryi1());
            vbox.getChildren().addAll(x, y);
        }
        else
            vbox.getChildren().add(new Label(metodo.obtenerRaiz() + ""));
    }

    Tabla(Ecuacion metodo)
    {
        this.metodo = metodo;
        datos = FXCollections.observableArrayList();
        crearUI();
        configurarTabla();
        System.out.println(this.metodo.errorU);
        cargarTabla(metodo.listaIteraciones);
        this.setTitle("Tabla del método " + metodo.titulo);
        this.setScene(escena);
        this.show();
    }
}
