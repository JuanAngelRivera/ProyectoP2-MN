package com.example.proyecto2pmn.reglaFalsa;
import com.example.proyecto2pmn.Ecuacion;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Algoritmo extends Ecuacion
{
    public Double a, b, fa, fb, xr, fxr, errorU, errorAbs;
    public Algoritmo()
    {
        super("Regla Falsa");
        super.descripcion = "El método de la regla falsa o regla falsa simple es un procedimiento antiguo para encontrar raíces" +
                " aproximadas de ecuaciones. Se originó en la matemática babilónica, hace más de 3,000 años. Sin embargo, su" +
                " desarrollo formal y sistemático dentro del contexto de los métodos numéricos modernos se atribuye a los " +
                "matemáticos europeos durante el Renacimiento.\nMás adelante, el método fue formalizado y mejorado en el siglo" +
                " XVIII. A veces se le atribuye al matemático francés Joseph-Louis Lagrange (1736-1813), quien trabajó en métodos" +
                " de aproximación y de raíces de ecuaciones, aunque no fue el creador original del método.";
        super.columnasTabla = new ArrayList<>(Arrays.asList("No.", "a", "b", "f(a)", "f(b)", "Xr", "f(Xr)", "error"));
        super.parametros(new String[]{"a", "b", "error"});
        super.listaIteraciones = new ArrayList<>();
    }
    /**
     f(x) = x^3 - 4x + 1
     */
    public static double f(double x)
    {
        return Math.pow(x, 3) - 4*x + 1;
    }

    public void calcularIteraciones()
    {

        fa = evaluarFuncion(a);
        fb = evaluarFuncion(b);

        if (fa * fb > 0)
        {
            System.out.println("\nError: f(a) y f(b) deben tener signos opuestos al evaluarse en la ecuacion.");
            return;
        }

        int iteracion = 1;
        xr = 0.0;
        Double xrAnterior = 0.0;

        int maxIteraciones = 200;

        while (true)
        {
            iteracion++;

            fa = redondear(evaluarFuncion(a));
            fb = redondear(evaluarFuncion(b));

            xr = redondear(( a * fb - b * fa) / (fb - fa));
            fxr = redondear(evaluarFuncion(xr));
            if(iteracion == 1)
                errorAbs = redondear(100.0);
            else
                errorAbs = redondear(Math.abs(xr - xrAnterior) / xr * 100);

            System.out.printf("%2d\t%10.6f\t%10.6f\t%10.6f\t%10.6f\t%10.6f\t%10.6f\t%10.6f\n",
                    iteracion, a, b, fa, fb, xr, fxr, errorAbs);

            String[] datos = new String[]{iteracion + "", a + "", b + "", fa + "", fb + "", xr + "", fxr + "", errorAbs + "%"};
            listaIteraciones.add(datos);


            if (iteracion > 1 && errorAbs < errorU)
                break;

            if (fa * fxr < 0)
                b = xr;
            else
                a = xr;

            xrAnterior = xr;

            if (iteracion >= maxIteraciones)
            {
                System.out.println("\nSe alcanzo el número maximo de iteraciones sin converger.");
                System.out.printf("Último valor de Xr la raiz aproximada: %.6f\n", xr);
                break;
            }
        }
    }

    @Override
    public double obtenerRaiz() {
        return xr;
    }

    public double obtenerErrorU()
    {
        return errorU;
    }

    @Override
    public void valoresParametro(Double[] parametros)
    {
        a = redondear(parametros[0]);
        b = redondear(parametros[1]);
        errorU = redondear(parametros[2]);
        errorAbs = redondear(1.0);
    }
}
