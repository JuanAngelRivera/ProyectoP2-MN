package com.example.proyecto2pmn.newtonRhapson;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Procedimiento {
    private String fun;
    private double xi;
    private double xi1;
    private double fx;
    private double fxp;
    private double error;
    private Derivar derivar = new Derivar();
    private List<Object[]> lista = new ArrayList<>();

    private static final DecimalFormat df = new DecimalFormat("#.######");

    public Procedimiento(String fun, double x) {
        this.fun = fun;
        this.xi = redondear(x);
        derivar.setFun(this.fun);
        derivar.derivar();
        this.error = 1.0;
    }

    public String obtenerFuncion() {
        return this.fun;
    }

    public String obtenerDerivada() {
        return derivar.getfun();
    }

    public List<Object[]> calcularTabla() {
        int n = 1;
        lista.clear();


        while (this.error > 0.0001) {
            this.fx = redondear(derivar.evaluarfuncionOriginal(xi));
            this.fxp = redondear(derivar.evaluarfuncionDerivada(xi));
            this.xi1 = redondear(this.xi - (fx / fxp));
            this.error = Math.abs(xi1 - this.xi) / Math.abs(xi1);
            double errorPorcentaje = redondear(this.error * 100);

            lista.add(new Object[]{n, this.xi, this.fx, this.fxp, this.xi1, errorPorcentaje+" %"});
            this.xi = this.xi1;
            n++;
        }

        return lista;
    }

    private double redondear(double x) {
        return Double.parseDouble(String.format("%.6f", x));
    }

    public double obtenerRaiz() {
        return this.xi;
    }
}

