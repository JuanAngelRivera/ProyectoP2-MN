package com.example.proyecto2pmn.GaussJordan;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.linear.*;


public class Method {
    public static void main(String[] args){
        Method obj = new Method();
    }
    int a_numbFunc, a_numbCoefficient;
    String a_functions [];
    double a_coefficients [][];
    double a_constants [];
    boolean flag =true;
    Scanner sc = new Scanner(System.in);
    Method(){
        m_intrData();
    }
    void m_intrData(){
        System.out.println("The functions must be on terms of X");
        System.out.println("You must add all the coefficients of X, even if they are 0");
        System.out.println("Enter the number of functions you want to perform");
        a_numbFunc = Integer.parseInt(sc.nextLine());
        System.out.println("How many variables exists, enter your coefficients");
        a_numbCoefficient = Integer.parseInt(sc.nextLine());
        a_functions = new String[a_numbFunc];
        for (int i = 0; i < a_numbFunc; i++){
            System.out.println("Enter the function"+i+1+" you want to perform");
            a_functions[i] = sc.nextLine();
        }
        a_coefficients = new double[a_numbFunc][a_numbCoefficient];
        a_constants = new double[a_numbFunc];
        m_concFunction();

    }
    void m_concFunction(){
        for (int i = 0; i < a_numbFunc; i++) {

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



        for (int i = 0; i < a_functions.length; i++) {
            String[] parts = a_functions[i].split("=");
            a_constants[i] = Double.parseDouble(parts[1].trim());
            //System.out.println(a_constants[i]);// Right side is the constant term
        }
        try{
            m_solveAndPrintSolution(a_coefficients, a_constants);

        }
        catch (Exception e){
            System.out.println("The system has no solution");

        }

    }

    public static void m_solveAndPrintSolution(double[][] coefficients, double[] constants) {
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

        if (freeDim > 0) {
            System.out.println("The system has infinite solutions. General solution:");
            // We'll use free parameter names: s, t, u, ...
            String[] paramNames = {"s", "t", "u", "v", "w", "r"};
            for (int i = 0; i < nVars; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("x").append(i + 1).append(" = ");
                // Start with the particular solution value.
                double part = particularSolution.getEntry(i);
                sb.append(formatCoefficient(part));
                // Then add the contribution from each free parameter.
                for (int k = 0; k < freeDim; k++) {
                    double coeff = nullSpaceBasis[k][i];
                    if (Math.abs(coeff) < 1e-10) continue;
                    // Choose sign and formatting.
                    if (coeff < 0) {
                        sb.append(" - ");
                    } else {
                        sb.append(" + ");
                    }
                    double absCoeff = Math.abs(coeff);
                    if (Math.abs(absCoeff - 1.0) < 1e-6) {
                        sb.append(paramNames[k]);
                    } else {
                        sb.append(formatCoefficient(absCoeff))
                                .append("*")
                                .append(paramNames[k]);
                    }
                }
                System.out.println(sb.toString());
            }
        } else {
            // In case the system has a unique solution.
            System.out.println("The system has a unique solution:");
            for (int i = 0; i < nVars; i++) {
                System.out.println("x" + (i + 1) + " = " + formatCoefficient(particularSolution.getEntry(i)));
            }
        }
    }

    /**
     * Computes a basis for the null space of matrix A (i.e. solutions of A*x = 0)
     * using a simple Gaussian elimination algorithm to obtain the reduced row echelon form.
     */
    public static double[][] computeNullSpace(RealMatrix A) {
        // Work on a copy of A's data.
        double[][] a = copyMatrix(A.getData());
        int m = a.length;
        int n = a[0].length;
        double tol = 1e-10;
        int row = 0;
        int[] pivotColumns = new int[m];
        for (int i = 0; i < m; i++) {
            pivotColumns[i] = -1;
        }

            // Gaussian elimination to obtain RREF.
            for (int col = 0; col < n && row < m; col++) {
                // Find the pivot row for this column.
                int pivot = row;
                while (pivot < m && Math.abs(a[pivot][col]) < tol) {
                    pivot++;
                }
                if (pivot == m) continue;  // No pivot in this column.
                // Swap rows if needed.
                if (pivot != row) {
                    double[] temp = a[row];
                    a[row] = a[pivot];
                    a[pivot] = temp;
                }
                pivotColumns[row] = col;
                // Normalize the pivot row.
                double pivotVal = a[row][col];
                for (int j = col; j < n; j++) {
                    a[row][j] /= pivotVal;
                }
                // Eliminate this column from all other rows.
                for (int r = 0; r < m; r++) {
                    if (r != row) {
                        double factor = a[r][col];
                        for (int j = col; j < n; j++) {
                            a[r][j] -= factor * a[row][j];
                        }
                    }
                }
                row++;

            }

            // Identify pivot columns.
            boolean[] isPivot = new boolean[n];
            for (int i = 0; i < m; i++) {
                if (pivotColumns[i] != -1) {
                    isPivot[pivotColumns[i]] = true;
                }
            }
            // Count free (non-pivot) columns.
            int freeCount = 0;
            for (int j = 0; j < n; j++) {
                if (!isPivot[j]) freeCount++;
            }
            // For each free variable, build a basis vector.
            double[][] nullSpace = new double[freeCount][n];
            int freeIndex = 0;
            for (int j = 0; j < n; j++) {
                if (!isPivot[j]) {
                    double[] vec = new double[n];
                    // Set the free variable j to 1.
                    vec[j] = 1;
                    // For each pivot row, determine the corresponding variable.
                    for (int i = 0; i < m; i++) {
                        if (pivotColumns[i] != -1) {
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
    public static double[][] copyMatrix(double[][] original) {
        int m = original.length;
        int n = original[0].length;
        double[][] copy = new double[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // Helper method: formats coefficients to avoid long decimals.
    private static String formatCoefficient(double x) {
        double tol = 1e-6;
        double rounded = Math.round(x * 1e6) / 1e6;
        if (Math.abs(rounded - Math.round(rounded)) < tol) {
            return String.valueOf((int)Math.round(rounded));
        }
        return String.format("%.4f", rounded);
    }
}


