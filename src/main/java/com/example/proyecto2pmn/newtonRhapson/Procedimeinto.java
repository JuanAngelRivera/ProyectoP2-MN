package com.example.proyecto2pmn.newtonRhapson;

public class Procedimeinto
{
    private String fun;
    private double xi;
    private double xi1;
    private double fx;
    private double fxp;
    private double error;
    Derivar derivar = new Derivar();

    public Procedimeinto(String fun, double x)
    {
        this.fun = fun;
        this.xi = decimales(x);

        derivar.setFun(this.fun);
        derivar.derivar();
        this.error = 1.0;
    }
    public String obtenerFuncion()
    {
        return this.fun;
    }
    public String obtenerDerivada()
    {
        String derivada = "";
        derivada= derivar.getfun();
        return derivada;
    }

    public String tabla()
    {
        int n=1;
        String tabla = "______________________________________________________________________\n";
              tabla += "|No |xi          |f(xi)       |f'(xi)      |xi+1        |error       |\n";
              tabla += "______________________________________________________________________\n";
              while(this.error > 0.0001) {

                  this.fx= derivar.evaluarfuncionOriginal(xi);
                  this.fx= decimales(this.fx);

                  this.fxp= derivar.evaluarfuncionDerivada(xi);
                  this.fxp= decimales(this.fxp);
                  this.xi1= this.xi-(fx/fxp);
                  this.xi1= decimales(this.xi1);
                  this.error = Math.abs(xi1-this.xi)/Math.abs(xi1);
                  this.error = decimales(this.error);
                  double errorpor= decimales(this.error*100);
                  tabla += String.format("|%-3s|%-12s|%-12s|%-12s|%-12s|%-12s|\n",n,this.xi,this.fx,this.fxp,this.xi1,errorpor);
                  this.xi= this.xi1;
                  n++;
              }
        tabla += "______________________________________________________________________\n";
              tabla+="La raiz aproximada con un error del 0.01% es: "+this.xi;

        return tabla;
    }

    private double decimales(double x){
        return Double.valueOf(String.format("%.6f",x));
    }
}
