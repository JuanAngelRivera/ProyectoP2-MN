package com.example.proyecto2pmn.NRmultivariable;

import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Derivar {
    private String fun = "";
    DJep djep;
    Node thisfun;
    Node fundx;
    Node fundy;

    // Usamos caracteres de control poco comunes como marcadores
    private final String markerPrefix = "\u0001";
    private final String markerSuffix = "\u0002";

    public Derivar() {
        this.djep = new DJep();
        this.djep.addStandardFunctions();
        this.djep.addStandardConstants();
        this.djep.addComplex();
        this.djep.setAllowUndeclared(true);
        this.djep.setAllowAssignment(true);
        this.djep.setImplicitMul(true);
        this.djep.addStandardDiffRules();
    }

    /**
     * Preprocesa la cadena de entrada para insertar la multiplicación implícita
     * y proteger los nombres de funciones (como log, ln, sin, etc.) usando
     * lookbehind/lookahead para forzar la protección incluso si están pegados a otros caracteres.
     */
    private String preprocess(String input) {
        // Lista de funciones a proteger (ordenadas de mayor a menor longitud)
        String[] functions = {
                "asin", "acos", "atan", "acot", "asec", "acsc",
                "sinh", "cosh", "tanh",
                "ln", "log", "sqrt", "exp", "abs",
                "sin", "cos", "tan", "cot", "sec", "csc"
        };
        // Paso 1: Proteger las funciones usando lookbehind/lookahead: (?<![A-Za-z]) y (?![A-Za-z])
        for (String func : functions) {
            // (?i) para case-insensitive
            // Esto coincide con el nombre func si no está precedido ni seguido por una letra.
            input = input.replaceAll("(?i)(?<![A-Za-z])" + func + "(?![A-Za-z])", markerPrefix + func + markerSuffix);
        }

        // Paso 2: Insertar '*' entre dígitos y letras y entre letras adyacentes en las regiones NO protegidas.
        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;
        Pattern tokenPattern = Pattern.compile(Pattern.quote(markerPrefix) + ".*?" + Pattern.quote(markerSuffix));
        Matcher matcher = tokenPattern.matcher(input);
        while (matcher.find()) {
            // Procesa el segmento fuera del token
            String segment = input.substring(lastIndex, matcher.start());
            // Inserta '*' entre dígitos y letras (ej.: "3x" → "3*x")
            segment = segment.replaceAll("(?<=\\d)(?=[A-Za-z])", "*");
            // Inserta '*' entre letras adyacentes (ej.: "xy" → "x*y")
            segment = segment.replaceAll("(?<=[A-Za-z])(?=[A-Za-z])", "*");
            sb.append(segment);
            // Añade el token sin cambios
            sb.append(matcher.group());
            lastIndex = matcher.end();
        }
        // Procesa el resto de la cadena
        String remainder = input.substring(lastIndex);
        remainder = remainder.replaceAll("(?<=\\d)(?=[A-Za-z])", "*");
        remainder = remainder.replaceAll("(?<=[A-Za-z])(?=[A-Za-z])", "*");
        sb.append(remainder);
        String processed = sb.toString();

        // Paso 3: Restaurar los nombres de función reemplazando los tokens por los nombres originales.
        for (String func : functions) {
            processed = processed.replace(markerPrefix + func + markerSuffix, func);
        }
        return processed;
    }

    public void setFun(String fun) {
        // Preprocesa la función antes de asignarla
        this.fun = preprocess(fun);
    }

    public String getfun() {
        return this.fun;
    }

    public String getfundx() {
        return this.djep.toString(this.fundx);
    }

    public String getfundy() {
        return this.djep.toString(this.fundy);
    }

    public void derivar() {
        try {
            // Parsear la función preprocesada
            this.thisfun = (Node) this.djep.parse(this.fun);
            // Derivada parcial respecto a x
            Node difx = this.djep.differentiate(this.thisfun, "x");
            this.fundx = this.djep.simplify(difx);
            // Derivada parcial respecto a y
            Node diffy = this.djep.differentiate(this.thisfun, "y");
            this.fundy = this.djep.simplify(diffy);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Evaluar usando el mismo DJep (sin crear otra instancia)
    public double evaluar(Node fun, double x, double y) {
        try {
            djep.addVariable("x", x);
            djep.addVariable("y", y);
            Object result = djep.evaluate(fun);
            if (result instanceof org.nfunk.jep.type.Complex) {
                return ((org.nfunk.jep.type.Complex) result).re();
            }
            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }
            throw new RuntimeException("Resultado no es un número válido.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double evaluarfuncionOriginal(double x, double y) {
        return evaluar(this.thisfun, x, y);
    }

    public double evaluarfuncionDerivadax(double x, double y) {
        return evaluar(this.fundx, x, y);
    }

    public double evaluarfuncionDerivaday(double x, double y) {
        return evaluar(this.fundy, x, y);
    }
}
