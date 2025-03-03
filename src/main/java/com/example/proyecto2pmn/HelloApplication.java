package com.example.proyecto2pmn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
    private Menu ecuaciones, abiertos, cerrados;
    private MenuItem reglaFalsa, newtonRhapson, gaussJordan;
    private VBox vbox;
    private Scene escena;

    public void create_ui()
    {
        reglaFalsa = new MenuItem("Regla Falsa");
        newtonRhapson = new MenuItem("Newton Rhapson");
        gaussJordan = new MenuItem("Gauss Jordan");

        abiertos = new Menu("Abiertos");
        abiertos.getItems().addAll(newtonRhapson, gaussJordan);
        cerrados = new Menu("Cerrados");
        cerrados.getItems().addAll(reglaFalsa);

        ecuaciones = new Menu("Ecuaciones");

        ecuaciones.getItems().addAll(abiertos, cerrados);
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