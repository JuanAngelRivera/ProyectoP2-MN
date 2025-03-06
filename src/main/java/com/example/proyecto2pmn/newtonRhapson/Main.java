package com.example.proyecto2pmn.newtonRhapson;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Ingresa la función a derivar:");
        String funcion = in.nextLine();

        System.out.println("Ingresa el valor inicial:");
        double x0 = in.nextDouble();

        Procedimiento procedimiento = new Procedimiento(funcion, x0);
        System.out.println("Función original: " + procedimiento.obtenerFuncion());
        System.out.println("Función derivada: " + procedimiento.obtenerDerivada());



        TablaResultados.setDatos(procedimiento.calcularTabla(), procedimiento.obtenerRaiz());


        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        TablaResultados tablaResultados = new TablaResultados();
        try {
            tablaResultados.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}