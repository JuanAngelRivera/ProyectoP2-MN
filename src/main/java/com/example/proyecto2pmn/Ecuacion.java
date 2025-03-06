package com.example.proyecto2pmn;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.util.List;

public class Ecuacion
{
    public String funcion;
    private JEP jep;
    private Node funcionTraducida;
    private List <String> parametros;

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

    private double evaluarFuncion(double x)
    {
        try
        {
            jep.setVarValue("x", x);
            return (double)(jep.evaluate(funcionTraducida));
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

    Ecuacion(String funcion)
    {
        this.funcion = funcion;
        this.jep = configJep();
        traducirFuncion();
    }
}
