package com.example.proyecto2pmn.RegresionMultiple;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String yStr, x1Str, x2Str;
        int nCantidad;
        Scanner sc = new Scanner(System.in);

        // Validar cantidad
        while (true) {
            System.out.println("Ingrese la cantidad de datos que tienes:");
            try {
                nCantidad = Integer.parseInt(sc.nextLine());
                if (nCantidad > 0) break;
                else System.out.println("Debe ser un numero entero positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ser un numero entero.");
            }
        }

        // Validar entrada de y
        yStr = leerCadenaNumeros(sc, "Ingrese la cantidad de y separadas por un espacio: ", nCantidad);

        // Validar entrada de x1
        x1Str = leerCadenaNumeros(sc, "Ingrese los valores de x1 separados por un espacio: ", nCantidad);

        // Validar entrada de x2
        x2Str = leerCadenaNumeros(sc, "Ingrese los valores de x2 separados por un espacio: ", nCantidad);

        // Llamada al metodo
        LinealMultiple obj = new LinealMultiple();
        obj.Metodomultiple(nCantidad, yStr, x1Str, x2Str);
    }


    private static String leerCadenaNumeros(Scanner sc, String mensaje, int cantidadEsperada) {
        while (true) {
            System.out.println(mensaje);
            String entrada = sc.nextLine().trim();
            String[] partes = entrada.split("\\s+");

            if (partes.length != cantidadEsperada) {
                System.out.println("Debe ingresar exactamente " + cantidadEsperada + " valores.");
                continue;
            }

            boolean esValido = true;
            for (String p : partes) {
                try {
                    Double.parseDouble(p);
                } catch (NumberFormatException e) {
                    esValido = false;
                    break;
                }
            }

            if (esValido) return entrada;
            else System.out.println("Solo se permiten numeros (enteros o decimales), sin letras ni símbolos.");
        }
    }
}

