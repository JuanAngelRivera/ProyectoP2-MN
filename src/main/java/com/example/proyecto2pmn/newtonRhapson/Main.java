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

//        //Algoritmo algoritmo = new Algoritmo(new );
//        System.out.println("Función original: " + algoritmo.obtenerFuncion());
//        System.out.println("Función derivada: " + algoritmo.obtenerDerivada());
//
//
//
//        TablaResultados.setDatos(algoritmo.calcularTabla(), algoritmo.obtenerRaiz());



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