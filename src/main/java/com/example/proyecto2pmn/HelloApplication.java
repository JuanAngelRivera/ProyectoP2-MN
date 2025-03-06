package com.example.proyecto2pmn;

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
    private Menu ecuaciones, abiertos, cerrados, lineales;
    private MenuItem reglaFalsa, newtonRhapson, gaussJordan, gaussSeidel;
    private VBox vbox;
    private Scene escena;

    public void create_ui()
    {
        reglaFalsa = new MenuItem("Regla Falsa");
        reglaFalsa.setOnAction(e -> new Interfaz(new Ecuacion()));

        newtonRhapson = new MenuItem("Newton Rhapson");
        newtonRhapson.setOnAction(e -> new Interfaz(new Ecuacion()));

        gaussJordan = new MenuItem("Gauss Jordan");
        gaussJordan.setOnAction(e -> new Interfaz(new Ecuacion()));

        gaussSeidel = new MenuItem("Gauss Seidel");
        gaussSeidel.setOnAction(e -> new Interfaz(new Ecuacion()));

        abiertos = new Menu("Abiertos");
        abiertos.getItems().addAll(newtonRhapson);
        cerrados = new Menu("Cerrados");
        cerrados.getItems().addAll(reglaFalsa);
        lineales = new Menu("Lineales");
        lineales.getItems().addAll(gaussJordan, gaussSeidel);

        ecuaciones = new Menu("Ecuaciones");

        ecuaciones.getItems().addAll(abiertos, cerrados, lineales);
        menu = new MenuBar();
        menu.getMenus().addAll(ecuaciones);
        vbox = new VBox(menu);
        escena = new Scene(vbox);
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