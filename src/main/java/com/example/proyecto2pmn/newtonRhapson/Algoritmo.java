package com.example.proyecto2pmn.newtonRhapson;

import com.example.proyecto2pmn.Ecuacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Algoritmo extends Ecuacion
{
    private double xi, xi1, fx, fxp, error;
    public double errorU;
    private Derivar derivar = new Derivar();
    private static final DecimalFormat df = new DecimalFormat("#.######");

    public Algoritmo()
    {
        super("Newton-Rhapson");
        super.listaIteraciones = new ArrayList<>();
        super.parametros(new String[]{"xi", "error"});
        super.columnasTabla = new ArrayList<>(Arrays.asList("No.", "xi", "f(xi)", "fp(xi)", "xi1", "error"));
        this.error = 1.0;
        super.descripcion = "El método de Newton-Raphson es un procedimiento iterativo para encontrar aproximaciones de raíces de una función real. Se basa en la linealización de la función (usando su derivada) y se repite hasta alcanzar la precisión deseada.\n" +
                "El método fue introducido por Isaac Newton alrededor de 1669, pero fue Joseph Raphson quien lo publicó en 1690 y le dio una formulación más sencilla y general. De ahí el nombre Newton-Raphson.\n" +
                "Es uno de los métodos más importantes y utilizados en el análisis numérico por su rapidez de convergencia en funciones suaves.";
    }

    public void calcularIteraciones()
    {
        if (!super.listaIteraciones.isEmpty())
            error = 1.0;

        int n = 1;
        super.listaIteraciones = new ArrayList<>();

        while (this.error > errorU)
        {
            this.fx = redondear(derivar.evaluarfuncionOriginal(xi));
            this.fxp = redondear(derivar.evaluarfuncionDerivada(xi));
            this.xi1 = redondear(this.xi - (fx / fxp));
            this.error = Math.abs(xi1 - this.xi) / Math.abs(xi1);
            double errorPorcentaje = redondear(this.error * 100);

            String[] datos = new String[]{n + "", this.xi + "", this.fx + "", this.fxp + "", this.xi1 + "", errorPorcentaje + "%"};

            listaIteraciones.add(datos);
            this.xi = this.xi1;
            n++;
        }
    }

    public void valoresParametro(Double[] valores)
    {
        this.xi = valores[0];
        this.errorU = valores[1];
        derivar.setFun(super.funcion);
        derivar.derivar();
    }

    public double obtenerRaiz()
    {
        return this.xi;
    }

    public double obtenerErrorU(){return this.errorU;}
}

