package com.example.proyecto2pmn;

import com.example.proyecto2pmn.Lineal.Lineal;
import com.example.proyecto2pmn.NRmultivariable.Algoritmo;
import com.example.proyecto2pmn.RegresionMultiple.LinealMultiple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application
{
    private MenuBar menu;
    private Menu ecuaciones, abiertos, cerrados, lineales, extra, regresion, botonSalir;
    private MenuItem reglaFalsa, newtonRhapson, gaussJordan, gaussSeidel, gaussJordanMV, info, regresionLineal,
            regresionLinealMultiple;
    private VBox vbox;
    private Scene escena;
    private ContextMenu infoContextMenu;

    public void create_ui()
    {
        botonSalir = new Menu("Salir");
        botonSalir.getItems().add(new MenuItem());
        botonSalir.setOnShowing(event -> Platform.exit());


        reglaFalsa = new MenuItem("Regla Falsa");
        reglaFalsa.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.reglaFalsa.Algoritmo()));

        newtonRhapson = new MenuItem("Newton Rhapson");
        newtonRhapson.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.newtonRhapson.Algoritmo()));

        gaussJordan = new MenuItem("Gauss Jordan");
        gaussJordan.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.GaussJordan.Algoritmo()));

        gaussSeidel = new MenuItem("Gauss Seidel");
        gaussSeidel.setOnAction(e -> new Interfaz(new com.example.proyecto2pmn.GaussSeidel.Algoritmo()));

        gaussJordanMV = new MenuItem("Gauss Jordan Multivariable");
        gaussJordanMV.setOnAction(event -> new Interfaz(new Algoritmo()));

        regresionLineal = new MenuItem("Regresion Lineal");
        regresionLineal.setOnAction(event -> new Interfaz(new Lineal()));
        regresionLinealMultiple = new MenuItem("Regresion Lineal Multiple");
        regresionLinealMultiple.setOnAction(event -> new Interfaz(new LinealMultiple()));

        info = new MenuItem("Autores del programa");
        create_info();

        abiertos = new Menu("Abiertos");
        abiertos.getItems().addAll(newtonRhapson);
        cerrados = new Menu("Cerrados");
        cerrados.getItems().addAll(reglaFalsa);
        lineales = new Menu("Lineales");
        lineales.getItems().addAll(gaussJordan, gaussSeidel, gaussJordanMV);
        regresion = new Menu("Regresion");
        regresion.getItems().addAll(regresionLineal, regresionLinealMultiple);
        extra = new Menu("Acerca del programa");
        extra.getItems().addAll(info);
        ecuaciones = new Menu("Ecuaciones");
        ecuaciones.getItems().addAll(abiertos, cerrados, lineales);

        menu = new MenuBar();
        menu.getMenus().addAll(ecuaciones, regresion, extra, botonSalir);
        vbox = new VBox(menu);
        escena = new Scene(vbox);
        escena.getStylesheets().add(getClass().getResource("/css/general.css").toExternalForm());
    }

    public void create_info()
    {
        infoContextMenu = new ContextMenu();
        Label contenido = new Label("Programa creado por alumnos del Tecnológico Nacional de México en Celaya para la" +
                " materia de métodos numéricos\nIntegrantes:\n-Arriaga Vázquez Mariana\n-González Cervantes Esteban\n" +
                "-Hernández Luna Diego Leonardo\n-Rivera Torres Juan Angel\n-Silva Paniagua Jennifer María");
        contenido.setWrapText(true);
        CustomMenuItem customMenuItem = new CustomMenuItem(contenido, false);
        customMenuItem.setId("menu-item-info");
        infoContextMenu.getItems().add(customMenuItem);

        info.setOnAction(e -> {
            if (!infoContextMenu.isShowing()) {
                infoContextMenu.show(menu, Side.BOTTOM, 0, 0);
            } else {
                infoContextMenu.hide();
            }
        });
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