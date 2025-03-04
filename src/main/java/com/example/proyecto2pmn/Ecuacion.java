package com.example.proyecto2pmn;

import javafx.scene.chart.LineChart;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

public class Ecuacion
{
    public String funcion;
    private JEP jep;
    private Node funcionTraducida;
    private LineChart <Number, Number> gráfica;

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

    Ecuacion(String funcion)
    {
        this.funcion = funcion;
        this.jep = configJep();
        traducirFuncion();
    }

}
