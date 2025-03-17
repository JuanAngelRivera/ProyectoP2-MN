package com.example.proyecto2pmn.GaussSeidel;
import com.example.proyecto2pmn.VentanaError;

import static java.lang.Math.abs;

public class prueba
{
    public void solveAlgoritm(double[][] coef,double[] result,double error,double []indep)
    {
        int iteraciones = 0;
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

        mostrarX(despeje);
        despeje=calcularXn(despeje);
        mostrarX(despeje);

        while(calcularError(despejeCopy,despeje,error))
        {
            despejeCopy = calcularXn(despejeCopy);
            despeje = calcularXn(despeje);
            mostrarXn(despejeCopy);
            mostrarXn(despeje);
            iteraciones++;

            if (iteraciones == 200)
            {
                new VentanaError("No existe convergencia!");
                break;
            }

        }
        if(iteraciones < 200)
        {
            mostrarResultado(despeje);
        }
    }

    public void mostrar(double [][] matriz)
    {
        for (int i = 0; i < matriz.length; i++)
        {
            for (int j = 0; j < matriz[i].length; j++)
            {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }

    }
    //METODO QUE ME MOSTRARA EN CONSOLA SOLO EL ULTIMO RENGLON QUE ALMACENA MATRIZ DE ERROR
    public void mostrarX(double[][] matriz)
    {
        int i=matriz.length-1;
        for (int j = 0; j < matriz[0].length; j++)
        {
            System.out.print(matriz[i][j]+" ");
        }
    }
    //METODO QUE ME MOSTRARA EN CONSOLA SOLO EL ULTIMO RENFLON QUE ALMACENA MATRIZ DESPEJE Y COPIA
    public void mostrarXn(double[][] matriz)
    {
        int i=matriz.length-1;
        for (int j = 0; j < matriz[0].length-1; j++)
        {
            System.out.print(matriz[i][j]+" ");
        }

    }
    //METODO MUESTRA RESULTADO
    public void mostrarResultado(double[][] matriz)
    {
        int i=matriz.length-1;
        for (int j = 0; j < matriz[0].length-1; j++)
        {
            System.out.print("x"+(j+1)+"= "+matriz[i][j]+" ");
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
        double[][] matrizError = new double[3][matrizAnt[0].length-1];
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

        //System.out.print("MATRIZ ERROR: ");
        mostrarX(matrizError);
        System.out.println();

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
