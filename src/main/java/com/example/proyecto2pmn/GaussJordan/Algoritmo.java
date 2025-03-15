package com.example.proyecto2pmn.GaussJordan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.proyecto2pmn.Ecuacion;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.apache.commons.math3.linear.*;


public class Algoritmo extends Ecuacion
{
    public int a_numbFunc, a_numbCoefficient;
    public static String estado;
    public String a_functions [];
    public double a_coefficients [][];
    public double a_constants [];
    public static ArrayList<String> resultados;
    boolean flag =true;
    Scanner sc = new Scanner(System.in);

    @Override
    public double obtenerRaiz()
    {
        return 0;
    }

    @Override
    public double obtenerErrorU() {
        return 0;
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
        m_concFunction();

    }
    void m_concFunction()
    {
        estado = "";
        for (int i = 0; i < a_numbFunc; i++)
        {

            Pattern pattern = Pattern.compile("(-?\\d*)x\\d+");
            Matcher matcher = pattern.matcher(a_functions[i]);
            int col = 0;
            while (matcher.find()) {
                String coeffStr = matcher.group(1);
                double coefficient = coeffStr.isEmpty() || coeffStr.equals("+") ? 1 : coeffStr.equals("-") ? -1 : Integer.parseInt(coeffStr);
                a_coefficients[i][col++] = coefficient;
                //System.out.println("Coefficient "+coefficient);
            }
        }


        System.out.println(a_functions[0]);
        System.out.println(a_functions[1]);
        System.out.println(a_functions[2]);
        for (int i = 0; i < a_functions.length; i++)
        {
            String[] parts = a_functions[i].split("=");
            a_constants[i] = Double.parseDouble(parts[1].trim());
            //System.out.println(a_constants[i]);// Right side is the constant term
        }
        try
        {
            m_solveAndPrintSolution(a_coefficients, a_constants);

        }
        catch (Exception e)
        {
            estado = "El sistema no tiene solución";

        }

    }

    public static void m_solveAndPrintSolution(double[][] coefficients, double[] constants)
    {
        RealMatrix A = new Array2DRowRealMatrix(coefficients);
        RealVector B = new ArrayRealVector(constants);
        int nVars = A.getColumnDimension();

        // Get a particular (minimum-norm) solution using QR decomposition.
        // In an underdetermined system, this is only one member of the infinite solution set.
        DecompositionSolver solver = new QRDecomposition(A).getSolver();
        RealVector particularSolution = solver.solve(B);

        // Compute the null space basis for A*x = 0 using our custom method.
        double[][] nullSpaceBasis = computeNullSpace(A);
        int freeDim = nullSpaceBasis.length; // Number of free parameters

        if (freeDim > 0)
        {
            estado = "El sistema tiene soluciones infinitas. Solucion general:";
            // We'll use free parameter names: s, t, u, ...
            String[] paramNames = {"s", "t", "u", "v", "w", "r"};
            for (int i = 0; i < nVars; i++)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("x").append(i + 1).append(" = ");
                // Start with the particular solution value.
                double part = particularSolution.getEntry(i);
                sb.append(formatCoefficient(part));
                // Then add the contribution from each free parameter.
                for (int k = 0; k < freeDim; k++)
                {
                    double coeff = nullSpaceBasis[k][i];
                    if (Math.abs(coeff) < 1e-10) continue;
                    // Choose sign and formatting.
                    if (coeff < 0)
                        sb.append(" - ");
                    else
                        sb.append(" + ");

                    double absCoeff = Math.abs(coeff);
                    if (Math.abs(absCoeff - 1.0) < 1e-6)
                    {
                        sb.append(paramNames[k]);
                    }
                    else
                    {
                        sb.append(formatCoefficient(absCoeff))
                                .append("*")
                                .append(paramNames[k]);
                    }
                }
                System.out.println(sb.toString());
            }
        }
        else
        {
            // In case the system has a unique solution.
            estado = "El sistema tiene una solucion unica:";
            resultados = new ArrayList<>();

            for (int i = 0; i < nVars; i++)
            {
                resultados.add("x" + (i + 1) + " = " + formatCoefficient(particularSolution.getEntry(i)));
                System.out.println(resultados.get(i));
            }
        }
    }

    /**
     * Computes a basis for the null space of matrix A (i.e. solutions of A*x = 0)
     * using a simple Gaussian elimination algorithm to obtain the reduced row echelon form.
     */
    public static double[][] computeNullSpace(RealMatrix A)
    {
        // Work on a copy of A's data.
        double[][] a = copyMatrix(A.getData());
        int m = a.length;
        int n = a[0].length;
        double tol = 1e-10;
        int row = 0;
        int[] pivotColumns = new int[m];
        for (int i = 0; i < m; i++)
        {
            pivotColumns[i] = -1;
        }

            // Gaussian elimination to obtain RREF.
            for (int col = 0; col < n && row < m; col++)
            {
                // Find the pivot row for this column.
                int pivot = row;
                while (pivot < m && Math.abs(a[pivot][col]) < tol)
                {
                    pivot++;
                }
                if (pivot == m) continue;  // No pivot in this column.
                // Swap rows if needed.
                if (pivot != row)
                {
                    double[] temp = a[row];
                    a[row] = a[pivot];
                    a[pivot] = temp;
                }
                pivotColumns[row] = col;
                // Normalize the pivot row.
                double pivotVal = a[row][col];
                for (int j = col; j < n; j++)
                {
                    a[row][j] /= pivotVal;
                }
                // Eliminate this column from all other rows.
                for (int r = 0; r < m; r++)
                {
                    if (r != row)
                    {
                        double factor = a[r][col];
                        for (int j = col; j < n; j++)
                        {
                            a[r][j] -= factor * a[row][j];
                        }
                    }
                }
                row++;
            }

            // Identify pivot columns.
            boolean[] isPivot = new boolean[n];
            for (int i = 0; i < m; i++)
            {
                if (pivotColumns[i] != -1)
                    isPivot[pivotColumns[i]] = true;
            }
            // Count free (non-pivot) columns.
            int freeCount = 0;
            for (int j = 0; j < n; j++)
            {
                if (!isPivot[j]) freeCount++;
            }
            // For each free variable, build a basis vector.
            double[][] nullSpace = new double[freeCount][n];
            int freeIndex = 0;
            for (int j = 0; j < n; j++)
            {
                if (!isPivot[j])
                {
                    double[] vec = new double[n];
                    // Set the free variable j to 1.
                    vec[j] = 1;
                    // For each pivot row, determine the corresponding variable.
                    for (int i = 0; i < m; i++)
                    {
                        if (pivotColumns[i] != -1)
                        {
                            int pc = pivotColumns[i];
                            // In RREF, the pivot row reads: x_pc + (coefficients)*[free variables] = 0.
                            // So, x_pc = - (coefficient of free variable).
                            vec[pc] = -a[i][j];
                        }
                    }
                    nullSpace[freeIndex++] = vec;
                }
            }
            return nullSpace;
    }

    // Helper method: makes a deep copy of a 2D array.
    public static double[][] copyMatrix(double[][] original)
    {
        int m = original.length;
        int n = original[0].length;
        double[][] copy = new double[m][n];
        for (int i = 0; i < m; i++)
        {
            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // Helper method: formats coefficients to avoid long decimals.
    private static String formatCoefficient(double x)
    {
        double tol = 1e-6;
        double rounded = Math.round(x * 1e6) / 1e6;
        if (Math.abs(rounded - Math.round(rounded)) < tol)
            return String.valueOf((int)Math.round(rounded));
        return String.format("%.4f", rounded);
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

    public Algoritmo()
    {
        super();
        super.titulo = "Gauss-Jordan";
        super.descripcion = "Descripcion Gauss-Jordan";
    }
}


