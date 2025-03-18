package com.example.proyecto2pmn.GaussSeidel;

import com.example.proyecto2pmn.Ecuacion;
import com.example.proyecto2pmn.VentanaError;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Algoritmo extends Ecuacion
{
    public int a_numbFunc, a_numbCoefficient;
    public static String estado;
    public String a_functions [];
    public double a_coefficients [][], matrizError[][];
    public double a_constants [], a_numbIndep[];
    public double errorU;
    public static ArrayList<String> resultados, variables;
    boolean flag =true;

    public Algoritmo()
    {
        super("Gauss-Seidel");
        super.descripcion = "El método de Gauss-Seidel es un método iterativo para resolver sistemas de ecuaciones lineales, especialmente útil cuando se tienen sistemas grandes y dispersos.\n" +
                "Fue publicado en 1823 por Carl Friedrich Gauss, aunque el método tal como se conoce fue desarrollado por Philipp Ludwig von Seidel en 1874, quien refinó el procedimiento de Gauss. En este método, las soluciones se actualizan inmediatamente a medida que se calculan, lo que mejora la convergencia bajo ciertas condiciones (matrices diagonalmente dominantes o positivas definidas).";
        super.listaIteraciones = new ArrayList<>();
        super.parametros(new String[]{"Error permitido: "});
    }

    @Override
    public double obtenerRaiz()
    {
        return 0;
    }

    @Override
    public double obtenerErrorU()
    {
        return errorU;
    }

    public void cargarColumnasVariables(String [] datos)
    {
        variables = new ArrayList<>();
        variables.add("No.");
        variables.addAll(Arrays.asList(datos));
        for (int i = 0; i < datos.length; i++)
        {
            variables.add("x" + (i + 1) + "N");
        }
        for (int i = 0; i < datos.length; i++)
        {
            variables.add("e" + (i + 1));
        }
        super.columnasTabla = variables;
    }

    @Override
    public void valoresParametro(Double[] parametros)
    {

    }

    @Override
    public void calcularIteraciones()
    {
        m_intrData();
    }

    void m_intrData()
    {
        a_coefficients = new double[a_numbFunc][a_numbCoefficient];
        a_constants = new double[a_numbFunc];
        a_numbIndep = new double[a_numbFunc];
        m_concFunction();
    }

    void m_concFunction()
    {
        for (int i = 0; i < a_numbFunc; i++)
        {
            System.out.println(a_functions[i]);
            Pattern pattern = Pattern.compile("([+-]?\\d*(?:\\.\\d+)?)x\\d+");
            Matcher matcher = pattern.matcher(a_functions[i]);
            int col = 0;
            while (matcher.find())
            {
                String coeffStr = matcher.group(1);
                double coefficient;
                if(coeffStr.isEmpty() || coeffStr.equals("+"))
                    coefficient = 1;
                else if(coeffStr.equals("-"))
                    coefficient = -1;
                else
                    coefficient = Double.parseDouble(coeffStr);
                a_coefficients[i][col++] = coefficient;
            }
        }

        for (int i = 0; i < a_functions.length; i++)
        {
            String[] parts = a_functions[i].split("=");
            a_constants[i] = Double.parseDouble(parts[1].trim());
        }
        try
        {
            solveAlgoritm(a_coefficients, a_constants, errorU, a_numbIndep);
        }
        catch (Exception e)
        {
            estado = "El sistema no tiene solución";
            e.printStackTrace();
        }
    }

    public GridPane crearGridPane ()
    {
        GridPane gridPane = new GridPane();

        // Recuerda: +1 para incluir encabezados y +1 para incluir la columna "="
        for (int i = 0; i < a_numbFunc + 1; i++) {
            for (int j = 0; j < a_numbCoefficient + 2; j++) { // +2 para la columna "="

                if (i == 0 || j == 0) {
                    // Labels de encabezado
                    Label label;

                    // Primera celda vacía
                    if (i == 0 && j == 0) {
                        label = new Label("");
                    }
                    // Primera fila: encabezados de las columnas X1, X2, ..., "="
                    else if (i == 0) {
                        if (j == a_numbCoefficient + 1) { // Última columna
                            label = new Label("=");
                        } else {
                            label = new Label("X" + j);
                        }
                    }
                    // Primera columna: encabezados de las filas f1, f2, ...
                    else {
                        label = new Label("f" + i);
                    }

                    gridPane.add(label, j, i);
                }
                else {
                    // TextFields para ingresar valores
                    TextField textField = new TextField();

                    if (j == a_numbCoefficient + 1) {
                        textField.setPromptText("Resultado");
                    } else {
                        textField.setPromptText("X" + j);
                    }

                    gridPane.add(textField, j, i);
                }
            }
        }

        return gridPane;
    }

    public void solveAlgoritm(double[][] coef,double[] result,double error,double []indep)
    {
        int iteraciones = 1;
        double[][] despeje = new double[coef.length+1][coef[0].length+1];
        double[][] despejeCopy = new double[coef.length+1][coef[0].length+1];

        for (int i = 0; i < coef.length; i++)
        {
            for (int j = 0; j < coef[0].length; j++)
            {
                if(i==j)
                {
                    despeje[i][j] = coef[i][j];
                    despejeCopy[i][j] = coef[i][j];
                    if ((j+1==coef[0].length))
                    {
                        despeje[i][j+1] = result[i];
                        despejeCopy[i][j+1] = result[i];
                    }
                }
                else
                {
                    if (j+1==coef[0].length)
                    {
                        despeje[i][j+1] = result[i];
                        despeje[i][j] = -1*coef[i][j];

                        despejeCopy[i][j+1] = result[i];
                        despejeCopy[i][j] = -1*coef[i][j];
                    }
                    else
                    {
                        despeje[i][j] = -1 * coef[i][j];
                        despejeCopy[i][j] = -1 * coef[i][j];
                    }
                }
            }
        }

        for (int i = 0; i < coef[0].length; i++)
        {
            despeje[despeje.length-1][i] = Double.parseDouble(String.format("%.6f", indep[i]));
        }
        despeje = calcularXn(despeje);

        while(calcularError(despejeCopy,despeje,error))
        {
            System.out.println("ITERACION N " + iteraciones);
            String[] datos = new String[columnasTabla.size()];
            System.out.println("columnasTabla: " + columnasTabla.size());
            datos[0] = iteraciones + "";

            for (int i = 0; i < despejeCopy[0].length; i++)
            {
                datos[i + 1] = String.valueOf(despejeCopy[despejeCopy.length - 1][i]);
            }
            for (int i = 0; i < despeje[0].length; i++)
            {
                datos[i + despejeCopy.length] = String.valueOf(despeje[despeje.length - 1][i]);
            }
            for (int i = 0; i < matrizError[0].length; i++)
            {
                datos[despejeCopy.length + despeje.length + i - 1] = String.valueOf(matrizError[matrizError.length - 1][i]);
            }
            listaIteraciones.add(datos);

            despejeCopy = calcularXn(despejeCopy);
            despeje = calcularXn(despeje);
            iteraciones++;

            if (iteraciones == 200)
            {
                new VentanaError("No existe convergencia!");
                break;
            }
        }
    }

    //METODO QUE CALCULA LA XNUEVA
    double[][] calcularXn(double[][] matriz)
    {
        double x = 0;
        double division = 0;
        int contador = 0;
        int resultXrow = matriz.length-1;

        for (int k = 0; k < matriz.length-1; k++)
        {
            for (int l = 0; l < matriz[0].length; l++)
            {
                if(k == l)
                    division=matriz[k][l];
                else
                    x = x + (matriz[k][l] * matriz[resultXrow][l]);

                if (l == matriz[0].length-1)
                {
                    x = (x + matriz[k][l]) / division;
                    matriz[resultXrow][contador] = Double.parseDouble(String.format("%.6f", x));
                    contador++;
                }
            }
            x = 0;
        }
        return matriz;
    }

    //METODO QUE CALCULA EL ERROR
    boolean calcularError(double [][] matrizAnt,double[][] matrizAct ,double error)
    {
        matrizError = new double[3][matrizAnt[0].length-1];
        boolean nocumple = true;
        int contador = 0;

        for (int j = 0; j < matrizAnt.length-1; j++)
        {
            matrizError[0][j] = matrizAnt[matrizAnt[0].length-1][j];
            matrizError[1][j] = matrizAct[matrizAct[0].length-1][j];
        }
        for (int i = 0; i < matrizError[0].length; i++)
        {
            matrizError[2][i] = Double.parseDouble(String.format("%.6f",abs(((matrizError[1][i] - matrizError[0][i]) / matrizError[1][i]) * 100)));
        }

        for (int i = 0; i < matrizError[0].length; i++)
        {
            if (matrizError[2][i] < error)
                contador++;
            else
                nocumple = true;

            if (contador == matrizError[0].length-1)
                nocumple = false;
        }
        return nocumple;
    }
}