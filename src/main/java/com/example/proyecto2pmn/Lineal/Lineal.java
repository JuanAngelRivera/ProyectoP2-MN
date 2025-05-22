package com.example.proyecto2pmn.Lineal;

import com.example.proyecto2pmn.Ecuacion;

import java.util.ArrayList;

public class Lineal extends Ecuacion
{
    double[] valoresx;
    double[] valoresy;
    double m;
    double b;
    double r;
    String resultado;
    public static ArrayList<String> variables;
    public Lineal(/*double[] valoresx, double[] valoresy*/)
    {
        super("Regresion lineal");
        super.listaIteraciones = new ArrayList<>();
        cargarColumnas();
        super.descripcion = "La regresión lineal simple es un método estadístico que permite modelar la relación entre una "
    + "variable dependiente y una variable independiente usando una recta (una función lineal). Su objetivo es encontrar la recta"
    + " que mejor se ajusta a los datos, minimizando la suma de los errores cuadrados entre los valores observados y los valores"
    +" predichos por el modelo.\nEste método tiene sus raíces en el trabajo del matemático Francis Galton en el siglo XIX, quien"
    + " estudió la herencia de características físicas y usó el término \"regresión\" para describir cómo las alturas de los"
    + " hijos tendían a “regresar” hacia la media. Luego, el estadístico Karl Pearson formalizó el concepto con una base"
    +" matemática más sólida.";
        /*this.valoresx = valoresx;
        this.valoresy = valoresy;*/
    }

    public void cargarColumnas()
    {
        variables = new ArrayList<>();
        variables.add("    ");
        variables.add("x");
        variables.add("y");
        variables.add("xy");
        variables.add("x^2");
        variables.add("y^2");
        super.columnasTabla = variables;
    }
    public void cargarRedon()
    {
        for(int i = 0; i < valoresx.length; i++)
        {
            valoresx[i] = super.redondear(valoresx[i]);
            valoresy[i] = super.redondear(valoresy[i]);
        }
    }

    public void calcular()
    {
        cargarRedon();
        String [] datos;
        double sumax = 0.0;
        double sumay = 0.0;
        double sumaxy = 0.0;
        double sumax2 = 0.0;
        double sumay2 = 0.0;
        for (int i = 0; i < valoresx.length; i++) {
            sumax += valoresx[i];
            sumay += valoresy[i];
            sumaxy += valoresx[i] * valoresy[i];
            sumax2 += valoresx[i] * valoresx[i];
            sumay2 += valoresy[i] * valoresy[i];
            datos = new String[]{"    ", valoresx[i]+"", valoresy[i]+"", valoresx[i]*valoresy[i]+"",valoresx[i]*valoresx[i]+""+valoresy[i]*valoresy[i]+""};
            listaIteraciones.add(datos);
        }
        datos = new String[]{"SUMA", sumax + "", sumay + "", sumaxy + "", sumax2 + "", sumay2 + ""};
        listaIteraciones.add(datos);
        m = (valoresx.length * sumaxy - sumax * sumay) / (valoresx.length * sumax2 - sumax * sumax);
        m = super.redondear(m);
        b = (sumay * sumax2 - sumax * sumaxy) / (valoresx.length * sumax2 - sumax * sumax);
        b = super.redondear(b);
        r = (valoresx.length * sumaxy - sumax * sumay)/(Math.sqrt((valoresx.length * sumax2 - sumax * sumax) *
                (valoresx.length * sumay2 - sumay * sumay)));
        r = super.redondear(r);
        //resultado+= "m= "+m+"\nb= "+b+"\nr= "+r+"\n";
        //resultado+="y= "+m+"x + "+b;
        //return resultado;
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
