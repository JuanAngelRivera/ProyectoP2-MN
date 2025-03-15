package com.example.proyecto2pmn.GaussSeidel;

import com.example.proyecto2pmn.Ecuacion;

public class Algoritmo extends Ecuacion
{
    public Algoritmo()
    {
        super("Gauss-Seidel");
        super.descripcion = "El método de Gauss-Seidel es un método iterativo para resolver sistemas de ecuaciones lineales, especialmente útil cuando se tienen sistemas grandes y dispersos.\n" +
                "Fue publicado en 1823 por Carl Friedrich Gauss, aunque el método tal como se conoce fue desarrollado por Philipp Ludwig von Seidel en 1874, quien refinó el procedimiento de Gauss. En este método, las soluciones se actualizan inmediatamente a medida que se calculan, lo que mejora la convergencia bajo ciertas condiciones (matrices diagonalmente dominantes o positivas definidas).";
    }

    @Override
    public double obtenerRaiz()
    {
        return 0;
    }

    @Override
    public double obtenerErrorU()
    {
        return 0;
    }

    @Override
    public void valoresParametro(Double[] parametros)
    {

    }

    @Override
    public void calcularIteraciones()
    {

    }
}
