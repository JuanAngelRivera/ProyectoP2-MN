package com.example.proyecto2pmn;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Ecuacion
{
    public String funcion, descripcion, titulo = "Newton-Rhapson";
    private JEP jep;
    private Node funcionTraducida;
    public ArrayList<String> parametros, columnasTabla;
    public ArrayList<String[]> listaIteraciones;

    private JEP configJep()
    {
        jep = new JEP();
        jep.addStandardFunctions();
        jep.addStandardConstants();
        jep.addVariable("x", 0);
        jep.setAllowAssignment(false);
        jep.setImplicitMul(true);
        return jep;
    }

    private void traducirFuncion()
    {
        try
        {

            funcionTraducida = (Node) jep.parse(funcion);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public double evaluarFuncion(double x)
    {
        try
        {
            jep.setVarValue("x", x);
            Object resultado = jep.evaluate(funcionTraducida);

            if (resultado instanceof Number)
                return ((Number) resultado).doubleValue();
            else
                return Double.NaN;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public LineChart <Number, Number> graficarFuncion(double minX, double maxX, double paso)
    {

        NumberAxis rangoX = new NumberAxis("x", minX, maxX, (maxX - minX) / 10);
        NumberAxis rangoY = new NumberAxis();
        rangoY.setLabel("f(x)");

        LineChart < Number, Number > lineChart = new LineChart<>(rangoX, rangoY);

        lineChart.setTitle("Gráfica de " + funcion);

        XYChart.Series < Number, Number > series = new XYChart.Series<>();
        series.setName("Funcion");

        for (double x = minX; x <= maxX; x += paso)
        {
            double y = evaluarFuncion(x);
            if (!Double.isNaN(y) && !Double.isInfinite(y))
                series.getData().add(new XYChart.Data(x, y));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    public void añadirFuncion(String funcion)
    {
        this.funcion = funcion;
        traducirFuncion();
    }

    public void titulo(String titulo)
    {
        this.titulo = titulo;
    }

    public void parametros(String [] campos)
    {
        parametros = new ArrayList<>();
        parametros.addAll(Arrays.asList(campos));
    }

    public double redondear(double x)
    {
        return Double.parseDouble(String.format("%.6f", x));
    }

    public abstract double obtenerRaiz();

    public abstract void valoresParametro(Double[] parametros);

    public abstract void calcularIteraciones();

    protected Ecuacion()
    {
        this.jep = configJep();
        parametros = new ArrayList<String>(Arrays.asList(""));
    }
}
