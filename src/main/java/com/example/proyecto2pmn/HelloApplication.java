package com.example.proyecto2pmn;

import com.example.proyecto2pmn.NRmultivariable.Algoritmo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application
{
    private MenuBar menu;
    private Menu ecuaciones, abiertos, cerrados, lineales, extra;
    private MenuItem reglaFalsa, newtonRhapson, gaussJordan, gaussSeidel, gaussJordanMV, info;
    private VBox vbox;
    private Scene escena;

    public void create_ui()
    {
        reglaFalsa = new MenuItem("Regla Falsa");
        reglaFalsa.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.reglaFalsa.Algoritmo()));

        newtonRhapson = new MenuItem("Newton Rhapson");
        newtonRhapson.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.newtonRhapson.Algoritmo()));

        gaussJordan = new MenuItem("Gauss Jordan");
        gaussJordan.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.GaussJordan.Algoritmo()));

        gaussSeidel = new MenuItem("Gauss Seidel");
        //gaussSeidel.setOnAction(e -> new Interfaz(new Ecuacion()));

        gaussJordanMV = new MenuItem("Gauss Jordan Multivariable");
        gaussJordanMV.setOnAction(event -> new Interfaz(new Algoritmo()));

        abiertos = new Menu("Abiertos");
        abiertos.getItems().addAll(newtonRhapson);
        cerrados = new Menu("Cerrados");
        cerrados.getItems().addAll(reglaFalsa);
        lineales = new Menu("Lineales");
        lineales.getItems().addAll(gaussJordan, gaussSeidel, gaussJordanMV);

        info = new MenuItem("Autores del programa");
        info.setOnAction(event -> new Info());
        extra = new Menu("Acerca del programa");
        extra.getItems().addAll(info);

        ecuaciones = new Menu("Ecuaciones");

        ecuaciones.getItems().addAll(abiertos, cerrados, lineales);
        menu = new MenuBar();
        menu.getMenus().addAll(ecuaciones, extra);
        vbox = new VBox(menu);
        escena = new Scene(vbox);
        escena.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());
    }
    @Override
    public void start(Stage stage) throws IOException
    {
        create_ui();
        stage.setScene(escena);
        stage.setTitle("Proyecto P2 MN");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}