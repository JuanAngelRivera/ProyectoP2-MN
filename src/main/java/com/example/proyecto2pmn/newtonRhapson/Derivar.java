package com.example.proyecto2pmn.newtonRhapson;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import java.text.DecimalFormat;
import static java.lang.Float.NaN;

public class Derivar
{
    private String fun="";
    DJep djep;
    Node thisfun;
    Node otrofun;

    public Derivar()
    {
        this.djep = new DJep();
        this.djep.addStandardFunctions();
        this.djep.addStandardConstants();
        this.djep.addComplex();
        this.djep.setAllowUndeclared(true);
        this.djep.setAllowAssignment(true);
        this.djep.setImplicitMul(true);
        this.djep.addStandardDiffRules();
    }

    public void setFun(String fun)
    {
        this.fun = fun;
    }

    public String getfun()
    {
        return this.fun;
    }

    public void derivar()
    {
        try
        {
            this.thisfun = (Node) this.djep.parse(this.fun);
            Node dif = this.djep.differentiate(this.thisfun,"x");
            this.otrofun= this.djep.simplify(dif);
            this.fun= this.djep.toString(this.otrofun);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }


    public double evaluarfuncionOriginal(double x)
    {
        String str= String.format("%.6f",evaluar(this.thisfun,x));
        double result = Double.valueOf(str);
        return result;
    }

    public double evaluarfuncionDerivada(double x)
    {
        String str= String.format("%.6f",evaluar(this.otrofun,x));
        double result = Double.valueOf(str);
        return result;
    }

    public double evaluar(Node fun, double x)
    {
        try
        {
            this.djep.addVariable("x", x);
            Object result = this.djep.evaluate(fun);
            if (result instanceof org.nfunk.jep.type.Complex)
                return ((org.nfunk.jep.type.Complex) result).re();

            if (result instanceof Number)

                return ((Number) result).doubleValue();
            else
                throw new RuntimeException("Resultado no es un número válido.");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
