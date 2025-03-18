package com.example.proyecto2pmn.GaussSeidel;

import com.example.proyecto2pmn.VentanaError;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
        Main obj = new Main();
    }
    int a_numbFunc, a_numbCoefficient;
    String a_functions [];
    double a_coefficients [][];
    double a_constants [];
    double a_error;
    double a_numbIndep[];
    boolean flag = true;
    Scanner sc = new Scanner(System.in);

    Main()
    {
        m_intrData();
    }

    void m_intrData()
    {
        a_error = Double.parseDouble(sc.nextLine());
        System.out.println("Enter the number of functions you want to perform");
        a_numbFunc = Integer.parseInt(sc.nextLine());
        a_numbIndep = new double[a_numbFunc];

        for (int i = 0; i < a_numbFunc; i++)
        {
            System.out.println("Ingresa valor de x"+(i+1));
            a_numbIndep[i] = Double.parseDouble(String.format("%.6f",Double.parseDouble(sc.nextLine())));
        }
        a_numbCoefficient = a_numbFunc ;
        a_functions = new String[a_numbFunc];
        for (int i = 0; i < a_numbFunc; i++)
        {
            System.out.println("Enter the function"+(i+1)+" you want to perform");
            a_functions[i] = sc.nextLine();
        }
        a_coefficients = new double[a_numbFunc][a_numbCoefficient];
        a_constants = new double[a_numbFunc];
        m_concFunction();

    }
    void m_concFunction()
    {
        for (int i = 0; i < a_numbFunc; i++)
        {
            Pattern pattern = Pattern.compile("([+-]?\\d*(?:\\.\\d+)?)x\\d+");
            Matcher matcher = pattern.matcher(a_functions[i]);
            int col = 0;
            while (matcher.find())
            {
                String coeffStr = matcher.group(1);
                double coefficient;
                if(coeffStr.isEmpty() || coeffStr.equals("+"))
                    coefficient = 1;
                else if(coeffStr.equals("-"))
                    coefficient = -1;
                else
                    coefficient = Double.parseDouble(coeffStr);
                a_coefficients[i][col++] = coefficient;
            }
        }

        for (int i = 0; i < a_functions.length; i++)
        {
            String[] parts = a_functions[i].split("=");
            a_constants[i] = Double.parseDouble(parts[1].trim());
        }
        try
        {
            System.out.println("Resolver");
            prueba algoritmo = new prueba();
            algoritmo.solveAlgoritm(a_coefficients, a_constants,a_error,a_numbIndep);
        }
        catch (Exception e)
        {
            new VentanaError("El sistema no tiene solución!");
        }
    }
}
