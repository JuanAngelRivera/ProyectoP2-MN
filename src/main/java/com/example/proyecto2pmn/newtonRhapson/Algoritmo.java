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
        super();
        super.listaIteraciones = new ArrayList<>();
        super.parametros(new String[]{"xi", "error"});
        super.columnasTabla = new ArrayList<>(Arrays.asList("No.", "xi", "f(xi)", "fp(xi)", "xi1", "error"));
        super.titulo("Newton-Rhapson");
        this.error = 1.0;
        super.descripcion = "MIAU";
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

