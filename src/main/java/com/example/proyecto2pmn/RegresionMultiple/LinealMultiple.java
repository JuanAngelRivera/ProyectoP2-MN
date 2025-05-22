package com.example.proyecto2pmn.RegresionMultiple;

import com.example.proyecto2pmn.Ecuacion;

import static java.lang.Math.sqrt;

public class LinealMultiple extends Ecuacion {
    int n;

    public LinealMultiple() {
        super("Regresion lineal Multiple");
        super.descripcion = "Regresion lineal Multiple";
    }

    public void Metodomultiple(int n,String y, String x1, String x2) {
        //crear arreglo n renglones por 10 columnas
        //y x1 x2 x1*x1 x1*x2 .....
        this.n = n;
        String entrada;
        String[] datos;
        entrada = y + " " + x1 + " " + x2;
        datos = entrada.split(" ");
        int count = 0;
        double[][] tabla = new double[n + 1][10];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n; j++) {
                tabla[j][i] = Double.parseDouble(datos[count]);
                count++;
            }
        }
        //ir llenando las columnas de la tabla
        tabla = elevarX1(tabla);
        tabla = multiplicarX1x2(tabla);
        tabla = elevarX2(tabla);
        tabla = multiplicarX1Y(tabla);
        tabla = multuplicarX2Y(tabla);
        tabla = suma(tabla);

        //hacer sistema de ecuaciones
        double[] resultado = new double[3];
        double[][] ecuaciones = new double[3][3];
        resultado[0] = tabla[n][0];
        resultado[1] = tabla[n][6];
        resultado[2] = tabla[n][7];

        ecuaciones[0][0]=n;
        ecuaciones[0][1]=tabla[n][1];
        ecuaciones[0][2]=tabla[n][2];
        ecuaciones[1][0]=tabla[n][1];
        ecuaciones[1][1]=tabla[n][3];
        ecuaciones[1][2]=tabla[n][4];
        ecuaciones[2][0]=tabla[n][2];
        ecuaciones[2][1]=tabla[n][4];
        ecuaciones[2][2]=tabla[n][5];
        mostrarEcuaciones(resultado,ecuaciones);
        double[] solucionEc;
        solucionEc=resolverSistema(ecuaciones,resultado);

        tabla=calcularSr(tabla,solucionEc);
        double promedioy=tabla[n][0]/n;
        tabla=calcularSt(tabla,promedioy);
        mostrar(tabla);
        mostrarSoluciones(solucionEc);
        double coefCorr=sqrt((tabla[n][9]-tabla[n][8])/tabla[n][9]);
        System.out.print("COEFICIENTE DE CORRELACION: ");
        System.out.printf("%.6f",coefCorr);


    }

    public void mostrar(double  [][] tabla){
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                System.out.printf("%.6f",tabla[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }
    public void mostrarEcuaciones(double[] resultados, double[][] coeficientes) {
        System.out.println("Ecuaciones");
        for (int i = 0; i < resultados.length; i++) {
            for (int j = 0; j < coeficientes[i].length; j++) {
                //System.out.print(coeficientes[i][j]+"a"+j+" + ");
                if(j+1<coeficientes[i].length){
                    System.out.print(coeficientes[i][j]+"a"+j+" + ");
                }
                else
                    System.out.print(coeficientes[i][j]+"a"+j);
            }
            System.out.print(" = "+resultados[i]);
            System.out.println();
        }
    }
    public void mostrarSoluciones(double[] solucion) {
        System.out.println("Soluciones de ecuaciones");
        for (double v : solucion) {
            System.out.println(v);
        }
    }
    double [][] elevarX1(double [][] tabla){
        for (int i =0 ; i < n; i++) {
            tabla[i][3]=tabla[i][1]*tabla[i][1];
        }
        return tabla;
    }

    double [][] multiplicarX1x2(double [][] tabla){
        for (int i =0 ; i < n; i++) {
            tabla[i][4]=tabla[i][1]*tabla[i][2];
        }
        return tabla;
    }
    double [][] elevarX2(double [][] tabla){
        for (int i =0 ; i < n; i++) {
            tabla[i][5]=tabla[i][2]*tabla[i][2];
        }
        return tabla;
    }
    double [][] multiplicarX1Y(double [][] tabla){
        for (int i =0 ; i < n; i++) {
            tabla[i][6]=tabla[i][0]*tabla[i][1];
        }
        return tabla;
    }
    double [][] multuplicarX2Y(double [][] tabla){
        for (int i =0 ; i < n; i++) {
            tabla[i][7]=tabla[i][0]*tabla[i][2];
        }
        return tabla;
    }
    double [][] suma(double [][] tabla){
        double suma=0;
        for (int i =0 ; i < 10; i++) {
            for (int j =0 ; j < n; j++) {
                suma+=tabla[j][i];
            }
            tabla[n][i]=suma;
            suma=0;
        }
        return tabla;
    }
    double [][] calcularSr(double [][] tabla,double []soluciones){
        for (int i =0 ; i < n; i++) {
            tabla[i][8]=Math.pow(tabla[i][0]-soluciones[0]-soluciones[1]*tabla[i][1]-soluciones[2]*tabla[i][2],2);
        }
        suma(tabla);
        return tabla;

    }
    double [][] calcularSt(double [][] tabla,double promedio){
        for (int i =0 ; i < n; i++) {
            tabla[i][9]=Math.pow(tabla[i][0]-promedio,2);
            suma(tabla);
        }
        return tabla;
    }

    public static double[] resolverSistema(double[][] matriz, double[] resultados) {
        int n = 3;

        // Formamos la matriz aumentada
        for (int i = 0; i < n; i++) {
            // Hacemos que el pivote sea distinto de cero
            if (matriz[i][i] == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (matriz[j][i] != 0) {
                        double[] temp = matriz[i];
                        matriz[i] = matriz[j];
                        matriz[j] = temp;

                        double tmp = resultados[i];
                        resultados[i] = resultados[j];
                        resultados[j] = tmp;
                        break;
                    }
                }
            }

            // Eliminación hacia adelante
            for (int j = i + 1; j < n; j++) {
                double factor = matriz[j][i] / matriz[i][i];
                for (int k = i; k < n; k++) {
                    matriz[j][k] -= factor * matriz[i][k];
                }
                resultados[j] -= factor * resultados[i];
            }
        }

        // Sustitución hacia atrás
        double[] soluciones = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double suma = resultados[i];
            for (int j = i + 1; j < n; j++) {
                suma -= matriz[i][j] * soluciones[j];
            }
            soluciones[i] = suma / matriz[i][i];
        }

        return soluciones;
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

    }
}
