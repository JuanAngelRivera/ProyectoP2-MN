package com.example.proyecto2pmn.Lineal;

import com.example.proyecto2pmn.Ecuacion;

import java.util.ArrayList;



public class Lineal extends Ecuacion {
    double[] valoresx;
    double[] valoresy;
    public static ArrayList<String> variables;
    public Lineal(double[] valoresx, double[] valoresy) {
        super("Regresion lineal");
        super.descripcion = "";
        super.listaIteraciones = new ArrayList<>();
        this.valoresx = valoresx;
        this.valoresy = valoresy;
    }

    public void cargarColumnas(){
        variables= new ArrayList<>();
        variables.add("    ");
        variables.add("x");
        variables.add("y");
        variables.add("xy");
        variables.add("x^2");
        variables.add("y^2");
        super.columnasTabla= variables;
    }
    public void cargarRedon(){
        for(int i = 0; i<valoresx.length; i++){
            valoresx[i] = Redondear(valoresx[i]);
            valoresy[i] = Redondear(valoresy[i]);
        }
    }

    public void calcular(){
        cargarRedon();
        String [] datos;
        double sumax=0.0;
        double sumay=0.0;
        double sumaxy=0.0;
        double sumax2=0.0;
        double sumay2=0.0;
        for (int i = 0; i < valoresx.length; i++) {
            sumax += valoresx[i];
            sumay += valoresy[i];
            sumaxy += valoresx[i]*valoresy[i];
            sumax2 += valoresx[i]*valoresx[i];
            sumay2 += valoresy[i]*valoresy[i];
            datos= new String[]{"    ", valoresx[i]+"", valoresy[i]+"", valoresx[i]*valoresy[i]+"",valoresx[i]*valoresx[i]+""+valoresy[i]*valoresy[i]+""};
            listaIteraciones.add(datos);
        }
        datos= new String[]{"SUMA",sumax+"",sumay+"",sumaxy+"",sumax2+"",sumay2+""};
        listaIteraciones.add(datos);
        double m=(valoresx.length*sumaxy-sumax*sumay)/(valoresx.length*sumax2-sumax*sumax);
        m= Redondear(m);
        double b=(sumay*sumax2-sumax*sumaxy)/(valoresx.length*sumax2-sumax*sumax);
        b=Redondear(b);
        double r= (valoresx.length*sumaxy-sumax*sumay)/(Math.sqrt((valoresx.length*sumax2-sumax*sumax)*(valoresx.length*sumay2-sumay*sumay)));
        r= Redondear(r);
        //resultado+= "m= "+m+"\nb= "+b+"\nr= "+r+"\n";
        //resultado+="y= "+m+"x + "+b;
        //return resultado;
    }

    public double Redondear(double x){
        x=Double.parseDouble(String.valueOf(String.format("%.6f",x)));
        return x;
    }

    @Override
    public double obtenerRaiz() {
        return 0;
    }

    @Override
    public double obtenerErrorU() {
        return 0;
    }

    @Override
    public void valoresParametro(Double[] parametros) {

    }

    @Override
    public void calcularIteraciones() {
        calcular();
    }
}
