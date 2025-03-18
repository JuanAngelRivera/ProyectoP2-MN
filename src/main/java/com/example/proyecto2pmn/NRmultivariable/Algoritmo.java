package com.example.proyecto2pmn.NRmultivariable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import com.example.proyecto2pmn.Ecuacion;

public class Algoritmo extends Ecuacion
{
    public String fun1, fun2, fun1dx, fun1dy, fun2dx, fun2dy;
    public Double xi, yi, errorU, xi1, yi1;
    Derivar f1, f2;

    public String valorxi1 ()
    {
        return String.valueOf(xi1);
    }

    public String valoryi1()
    {
        return String.valueOf(yi1);
    }
    public void setFun(String ecuacion1, String ecuacion2)
    {
        if (ecuacion1.contains("=")) {
            ecuacion1 = ecuacion1.substring(ecuacion1.indexOf("=") + 1).trim(); // Toma solo la parte derecha
        }

        fun1 = ecuacion1;
        fun2 = ecuacion2;
        System.out.println("Fun1: " + fun1);
        System.out.println("Fun2: " + fun2);

        f1 = new Derivar();
        f1.setFun(fun1);
        f1.derivar();
        fun1dx = f1.getfundx();
        fun1dy = f1.getfundy();

        f2 = new Derivar();
        f2.setFun(fun2);
        f2.derivar();
        fun2dx = f2.getfundx();
        fun2dy = f2.getfundy();
    }

    public void calcularIteraciones()
    {
        /*try
        {
            /*String p = "Python311\\python.exe";
            ProcessBuilder pb = new ProcessBuilder(p, "main.py", fun1, "y");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Leer la salida del script de Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println("1) y= : " + line);
            }

            pb = new ProcessBuilder(p, "main.py", fun2, "y");
            pb.redirectErrorStream(true);
            process = pb.start();

            // Leer la salida del script de Python
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line2;
            while ((line2 = reader.readLine()) != null)
            {
                System.out.println("2) y= : " + line2);
            }

            process.waitFor();  // Espera a que termine el proceso
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        double errorx = 100.0;
        double errory = 100.0;
        int n = 1;

        while(errorx > errorU && errory > errorU)
        {
            double fxy1= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionOriginal(xi,yi)));
            double df1x= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionDerivadax(xi,yi)));
            double df1y= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionDerivaday(xi,yi)));

            double fxy2= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionOriginal(xi,yi)));
            double df2x= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionDerivadax(xi,yi)));
            double df2y= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionDerivaday(xi,yi)));

            double increx = (-fxy1 * df2y + fxy2 * df1y) / (df1x * df2y - df1y * df2x);
            double increy = ( fxy1 * df2x - fxy2 * df1x) / (df1x * df2y - df1y * df2x);
            increx = Double.parseDouble(String.format("%.6f",increx));
            increy = Double.parseDouble(String.format("%.6f",increy));
             xi1= xi+increx;
             yi1= yi+increy;
            xi1= Double.parseDouble(String.format("%.6f",xi1));
            yi1= Double.parseDouble(String.format("%.6f",yi1));

            errorx=Math.abs(((xi1-xi)/xi1)*100);
            errory=Math.abs(((yi1-yi)/yi1)*100);

            errorx= Double.parseDouble(String.format("%.6f",errorx));
            errory= Double.parseDouble(String.format("%.6f",errory));

            String datos [] = new String[]{n + "", xi + "", yi + "", fxy1 + "", fxy2 + "", df1x + "", df1y + "", df2x + "",
            df2y + "", increx + "", increy + "", xi1 + "", yi1 + "", errorx + "", errory + ""};

            listaIteraciones.add(datos);
            xi = xi1;
            yi = yi1;
            n++;
        }
    }

    public Algoritmo()
    {
        super("Newton-Rhapson Multivariable");
        super.descripcion = "El método de Newton-Raphson para sistemas no lineales es una extensión del método de una variable, aplicada a varias ecuaciones no lineales simultáneamente.\n" +
                "Consiste en linealizar el sistema mediante la matriz Jacobiana de derivadas parciales, y resolver iterativamente el sistema de ecuaciones lineales resultante para aproximar la raíz del sistema completo.\n" +
                "Este enfoque se deriva directamente del trabajo de Newton y Raphson, pero su generalización al caso multivariable surgió de los desarrollos en análisis vectorial y cálculo diferencial en los siglos XVIII y XIX, particularmente con el desarrollo del concepto de Jacobiano.";
        super.listaIteraciones = new ArrayList<>();
        super.parametros(new String[]{"x0", "y0", "error permitido"});
        super.columnasTabla = new ArrayList<>(Arrays.asList("No.", "xi", "yi", "fxy1", "fxy2", "df1x", "df1y", "df2x",
                "df2y", "increx", "increy", "xi1", "yi1", "errorx", "errory"));
    }

    @Override
    public double obtenerRaiz()
    {
        return 0;
    }

    @Override
    public double obtenerErrorU()
    {
        return errorU;
    }

    @Override
    public void valoresParametro(Double[] parametros)
    {
        xi = parametros[0];
        yi = parametros[1];
        errorU = parametros[2];
    }
}