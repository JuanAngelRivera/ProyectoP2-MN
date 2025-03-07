package com.example.proyecto2pmn;

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
    TableView <String> tabla;
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

        for (String column : metodo.columnasTabla)
        {
            TableColumn <String, String> columna = new TableColumn<>(column);
            columna.setCellValueFactory(new PropertyValueFactory<>(column));
            tabla.getColumns().add(columna);
        }
    }

    public void cargarTabla(ArrayList<String[]> listaIteraciones)
    {
        List<Fila> filas = new ArrayList<>();
        for (int i = 0; i < listaIteraciones.size(); i++)
        {
            filas.add(new Fila(listaIteraciones.get(i)));
        }
        datos.setAll(filas);
    }

    Tabla(Ecuacion metodo)
    {
        this.metodo = metodo;
        datos = FXCollections.observableArrayList();
        crearUI();
        configurarTabla();
        cargarTabla(metodo.listaIteraciones);
        this.setTitle("Tabla del método " + metodo.titulo);
        this.setScene(escena);
        this.show();
    }
}
