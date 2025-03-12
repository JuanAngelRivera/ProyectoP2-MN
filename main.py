import sys
import re
from sympy import symbols, Eq, solve, sympify

def despejar(ecuacion_str, variable_str):
    try:
        # Convertir '^' a '**' para que SymPy interprete correctamente las potencias
        ecuacion_str = ecuacion_str.replace("^", "**")
        # Eliminar espacios innecesarios
        ecuacion_str = ecuacion_str.replace(" ", "")

        # Lista de funciones matemáticas comunes a proteger
        funciones = [
            'sin', 'cos', 'tan', 'cot', 'sec', 'csc',
            'asin', 'acos', 'atan', 'acot', 'asec', 'acsc',
            'sinh', 'cosh', 'tanh', 'coth', 'sech', 'csch',
            'asinh', 'acosh', 'atanh', 'acoth', 'asech', 'acsch',
            'exp', 'log', 'ln', 'sqrt', 'abs', 'floor', 'ceiling',
            'factorial', 'gamma', 'lgamma', 'erf', 'erfc'
        ]
        placeholders = {}
        # Proteger cada función reemplazándola por un token cuando vaya seguida de '('
        for func in funciones:
            placeholder = f"__{func.upper()}__"
            placeholders[placeholder] = func
            ecuacion_str = re.sub(r'(?i)' + re.escape(func) + r'(?=\()', placeholder, ecuacion_str)

        # Insertar '*' entre dígitos y letras o guiones bajos (ej.: "3x" → "3*x")
        ecuacion_str = re.sub(r"(\d)([_a-zA-Z])", r"\1*\2", ecuacion_str)
        # Insertar '*' entre letras adyacentes, evitando afectar los tokens protegidos
        ecuacion_str = re.sub(r"(?<!_)([a-zA-Z])([a-zA-Z])(?!_)", r"\1*\2", ecuacion_str)

        # Restaurar los nombres de funciones protegidas
        for placeholder, func in placeholders.items():
            ecuacion_str = ecuacion_str.replace(placeholder, func)

        # Guardar si se usó "ln(" en la entrada para luego preservar esa notación
        input_contains_ln = "ln(" in ecuacion_str.lower()

        # Definir la variable a despejar
        variable = symbols(variable_str)

        # Convertir la cadena en una expresión de SymPy
        ecuacion = sympify(ecuacion_str)
        # Crear la ecuación igualada a cero
        eq = Eq(ecuacion, 0)
        # Resolver la ecuación respecto a la variable
        soluciones = solve(eq, variable)

        # Seleccionar una solución: si hay dos, la segunda; sino la primera o un mensaje
        if len(soluciones) == 2:
            soluciones = soluciones[1]
        else:
            soluciones = soluciones[0] if soluciones else "No hay solución"

        # Convertir la solución a cadena y reemplazar '**' por '^'
        solucion_str = str(soluciones).replace("**", "^")

        # Si en la entrada se usó "ln", cambiar "log(" por "ln(" en la salida
        if input_contains_ln:
            solucion_str = solucion_str.replace("log(", "ln(")

        return solucion_str
    except Exception as e:
        return f"Error al procesar la ecuación: {e}"

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Uso: python script.py 'ecuacion' 'variable'")
    else:
        ecuacion = sys.argv[1]  # Ejemplo: "x+3ln(x)-y^2" o "2x^2-xy-5x+1"
        variable = sys.argv[2]  # Ejemplo: "y" o "x"
        resultado = despejar(ecuacion, variable)
        print(resultado)
