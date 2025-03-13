package com.example.proyecto2pmn.NRmultivariable;

import java.io.*;
import java.util.Scanner;

public class Algoritmo
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digiete la primera ecuacion multivariable f1(x,y)");
        String fun1= sc.nextLine();
        System.out.println("Digiete la segunda ecuacion multivariable f2(x,y)");
        String fun2= sc.nextLine();

        Derivar f1= new Derivar();
        f1.setFun(fun1);
        f1.derivar();

        System.out.println(f1.getfun()+" dx= "+f1.getfundx()+" dy= "+f1.getfundy());

        Derivar f2= new Derivar();
        f2.setFun(fun2);
        f2.derivar();
        System.out.println(f2.getfun()+" dx= "+f2.getfundx()+" dy= "+f2.getfundy());
        try
        {
            String p = "Python311\\python.exe";
            ProcessBuilder pb = new ProcessBuilder(p, "main.py", fun1, "y");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Leer la salida del script de Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println("1) y= : " + line);
            }

            pb = new ProcessBuilder(p, "main.py", fun2, "y");
            pb.redirectErrorStream(true);
            process = pb.start();

            // Leer la salida del script de Python
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line2;
            while ((line2 = reader.readLine()) != null)
            {
                System.out.println("2) y= : " + line2);
            }

            process.waitFor();  // Espera a que termine el proceso
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Digite el valor que le gustaria iniciar en x");
        double xi = sc.nextDouble();
        System.out.println("Digite el valor que le gustaria inicar en y");
        double yi = sc.nextDouble();

        System.out.println("Digite el error que le gustaria  manejar en porcentaje");
        double error = sc.nextDouble();

        double errorx=100.0;
        double errory=100.0;
        int n=1;
        String s="----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
              s+="|No        |xi        |yi        |f1        |f2        |df1/dx    |df1/dy    |df2/dx    |df2/dy    |inx       |iny       |xi+1      |yi+1      |errorx    |errory    |\n";
              s+="----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        while(errorx > error && errory > error)
        {
            double fxy1= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionOriginal(xi,yi)));
            double df1x= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionDerivadax(xi,yi)));
            double df1y= Double.parseDouble(String.format("%.6f",f1.evaluarfuncionDerivaday(xi,yi)));

            double fxy2= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionOriginal(xi,yi)));
            double df2x= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionDerivadax(xi,yi)));
            double df2y= Double.parseDouble(String.format("%.6f",f2.evaluarfuncionDerivaday(xi,yi)));

            double increx = (-fxy1 * df2y + fxy2 * df1y) / (df1x * df2y - df1y * df2x);
            double increy = ( fxy1 * df2x - fxy2 * df1x) / (df1x * df2y - df1y * df2x);
            increx = Double.parseDouble(String.format("%.6f",increx));
            increy = Double.parseDouble(String.format("%.6f",increy));
            double xi1= xi+increx;
            double yi1= yi+increy;
            xi1= Double.parseDouble(String.format("%.6f",xi1));
            yi1= Double.parseDouble(String.format("%.6f",yi1));

            errorx=Math.abs(((xi1-xi)/xi1)*100);
            errory=Math.abs(((yi1-yi)/yi1)*100);

            errorx= Double.parseDouble(String.format("%.6f",errorx));
            errory= Double.parseDouble(String.format("%.6f",errory));

            s+=String.format("|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|%-10s|",n,xi,yi,fxy1,fxy2,df1x,df1y,df2x,df2y,increx,increy,xi1,yi1,errorx,errory)+"\n";

            xi=xi1;
            yi=yi1;
            n++;
        }
        s+="----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
        s+= "Con un error de: "+error+"%\nLos resultados son: \nx= "+xi+"\ny= "+yi+"\n";
        System.out.println(s+"\n");
    }
}