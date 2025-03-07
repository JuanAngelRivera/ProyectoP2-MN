package com.example.proyecto2pmn.newtonRhapson;

import com.example.proyecto2pmn.Ecuacion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algoritmo extends Ecuacion
{
    private String fun;
    private double xi;
    private double xi1;
    private double fx;
    private double fxp;
    private double error;
    private Derivar derivar = new Derivar();
    private static final DecimalFormat df = new DecimalFormat("#.######");

    public Algoritmo()
    {
        super();
        super.parametros(new String[]{"xi"});
        super.columnasTabla = new ArrayList<String>(Arrays.asList("No.", "xi", "f(xi)", "fp(xi)", "xi1", "error"));
        super.titulo("Newton-Rhapson");
        this.error = 1.0;
    }

    public String obtenerFuncion() {
        return this.fun;
    }

    public String obtenerDerivada() {
        return derivar.getfun();
    }

    public void calcularIteraciones()
    {
        int n = 1;
        super.listaIteraciones = new ArrayList<>();

        while (this.error > 0.0001)
        {
            this.fx = redondear(derivar.evaluarfuncionOriginal(xi));
            this.fxp = redondear(derivar.evaluarfuncionDerivada(xi));
            this.xi1 = redondear(this.xi - (fx / fxp));
            this.error = Math.abs(xi1 - this.xi) / Math.abs(xi1);
            double errorPorcentaje = redondear(this.error * 100);

            String[] datos = new String[]{n + "", this.xi + "", this.fx + "", this.fxp + "", this.xi1 + "", errorPorcentaje + "%"};

            listaIteraciones.add(datos);
            //listaIteraciones.add(new Object[]{n, this.xi, this.fx, this.fxp, this.xi1, errorPorcentaje+" %"});
            this.xi = this.xi1;
            n++;
        }
    }

    public void valoresParametro(Double[] valores)
    {
        this.xi = valores[0];
        derivar.setFun(super.funcion);
        derivar.derivar();
    }

    public double obtenerRaiz()
    {
        return this.xi;
    }
}

