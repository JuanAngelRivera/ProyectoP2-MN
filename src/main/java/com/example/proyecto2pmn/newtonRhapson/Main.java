package com.example.proyecto2pmn.newtonRhapson;

import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Ingresa la funcion a derivar");
        String line = in.nextLine();
        System.out.println("Ingresa el valor de donde le gustaria empezar");
        double dond = in.nextDouble();
        Algoritmo p = new Algoritmo(line, dond);
        System.out.println("La funcion original es: " + p.obtenerFuncion());
        System.out.println("La funcion derivada es: " + p.obtenerDerivada());

        System.out.println(p.tabla());
    }
}