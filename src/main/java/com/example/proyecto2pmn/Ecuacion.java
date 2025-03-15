package com.example.proyecto2pmn;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Ecuacion
{
    public String funcion, descripcion, titulo;
    public double errorU;
    private JEP jep;
    private Node funcionTraducida;
    public ArrayList<String> parametros, columnasTabla;
    public ArrayList<String[]> listaIteraciones;
    public Image imagen;

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

    private boolean traducirFuncion()
    {
        if(validar(funcion))
        {
            if(validarf(funcion))
            {
                try {
                    funcionTraducida = jep.parse(funcion);
                    return true;
                }
                catch (ParseException e)
                {
                    new VentanaError(null);
                    e.printStackTrace();
                    return false;
                }
            }
            else
            {
                new VentanaError(null);
                return false;
            }
        }
        else
        {
            new VentanaError("¡Verifica tus paréntesis!");
            return false;
        }
    }

    private boolean validarf(String funcion)
    {
        String[] functions = {
                "asin", "acos", "atan", "acot", "asec", "acsc",
                "sinh", "cosh", "tanh",
                "ln", "log", "sqrt", "exp", "abs",
                "sin", "cos", "tan", "cot", "sec", "csc"
        };
        List<String> functionList = Arrays.asList(functions);

        Pattern p = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(funcion);

        while (m.find())
        {
            String match = m.group();
            if(match.equalsIgnoreCase("x"))
            {
                continue;
            }
            if (functionList.contains(match.toLowerCase()))
            {
                int indexAfter = m.end();
                while (indexAfter < funcion.length() && Character.isWhitespace(funcion.charAt(indexAfter)))
                {
                    indexAfter++;
                }
                if (indexAfter >= funcion.length() || funcion.charAt(indexAfter) != '(')
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }


    private boolean validar(String funcion)
    {
        int derecho = 0;
        int izquierda = 0;

        for (char c : funcion.toCharArray())
        {
            if (c == '(')
            {
                izquierda++;
            }
            if (c == ')')
            {
                derecho++;
            }
            if (derecho > izquierda)
            {
                return false;
            }
        }
        return izquierda == derecho;
    }


    public double evaluarFuncion(double x)
    {
        try
        {
            jep.setVarValue("x", x);
            Object resultado = jep.evaluate(funcionTraducida);

            if (resultado instanceof Number)
                return ((Number) resultado).doubleValue();
            else
                return Double.NaN;
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

    public boolean añadirFuncion(String funcion)
    {
        this.funcion = funcion;
        return traducirFuncion();
    }

    public void titulo(String titulo)
    {
        this.titulo = titulo;
    }

    public void parametros(String [] campos)
    {
        parametros = new ArrayList<>();
        parametros.addAll(Arrays.asList(campos));
    }

    public double redondear(double x)
    {
        return Double.parseDouble(String.format("%.6f", x));
    }

    public abstract double obtenerRaiz();

    public abstract double obtenerErrorU();

    public abstract void valoresParametro(Double[] parametros);

    public abstract void calcularIteraciones();

    protected Ecuacion(String titulo)
    {
        this.titulo = titulo;
        this.jep = configJep();
        parametros = new ArrayList<String>(Arrays.asList(""));
        System.out.println("/images/" + titulo + ".png");
        imagen = new Image(getClass().getResourceAsStream("/images/" + titulo + ".png"));
    }
}
