package com.example.proyecto2pmn;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Tabla extends Stage
{
    private Label titulo;
    private Ecuacion metodo;
    VBox vbox;
    Scene escena;
    TableView <String> tabla;

    public void crearUI()
    {
        titulo = new Label("Tabla del metodo " + metodo.titulo);
        tabla = new TableView<>();
        vbox = new VBox(titulo, tabla);
        escena = new Scene(vbox);
        configurarTabla();
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

    Tabla(Ecuacion metodo)
    {
        this.metodo = metodo;
        crearUI();
        this.setTitle("Tabla del método " + metodo.titulo);
        this.setScene(escena);
        this.show();
    }
}
