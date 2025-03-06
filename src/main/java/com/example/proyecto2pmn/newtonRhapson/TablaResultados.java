package com.example.proyecto2pmn.newtonRhapson;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.List;

class TablaResultados extends Application {
    private static List<Object[]> datos;
    private static double raiz;

    public TablaResultados() {
    }

    public static void setDatos(List<Object[]> lista, double raizAprox) {
        datos = lista;
        raiz = raizAprox;
    }

    @Override
    public void start(Stage stage) {
        TableView<Object[]> tableView = new TableView<>();

        TableColumn<Object[], String> noColumn = new TableColumn<>("No");
        TableColumn<Object[], String> xiColumn = new TableColumn<>("xi");
        TableColumn<Object[], String> fxColumn = new TableColumn<>("f(xi)");
        TableColumn<Object[], String> fxpColumn = new TableColumn<>("f'(xi)");
        TableColumn<Object[], String> xi1Column = new TableColumn<>("xi+1");
        TableColumn<Object[], String> errorColumn = new TableColumn<>("Error %");

        noColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        xiColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[1])));
        fxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[2])));
        fxpColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[3])));
        xi1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[4])));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue()[5])));

        tableView.getColumns().addAll(noColumn, xiColumn, fxColumn, fxpColumn, xi1Column, errorColumn);

        // Set columns to distribute evenly across the table's width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getItems().addAll(datos);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        Label label = new Label("Raíz aproximada con error <= 0.01%: " + raiz);

        VBox vbox = new VBox(10, tableView, label);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #ffffff;");

        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Tabla de Resultados - Newton-Raphson");
        stage.show();
    }
}