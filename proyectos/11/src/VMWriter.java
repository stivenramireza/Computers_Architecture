import java.util.HashMap;
/**
 * Esta clase permite escribir el archivo .vm usando los comandos del
 * lenguaje de máquina virtual de Jack.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class VMWriter {
    
    private static final HashMap<Character, String> operaciones = new HashMap<Character, String>();
    static {
        operaciones.put('+', "add"); // Operación aritmética
        operaciones.put('-', "sub"); // Operación aritmética
        operaciones.put('n', "neg"); // Operación aritmética
        operaciones.put('=', "eq"); // Operación condicional
        operaciones.put('>', "gt"); // Operación condicional
        operaciones.put('<', "lt"); // Operación condicional
        operaciones.put('&', "and"); // Operación lógica
        operaciones.put('|', "or"); // Operación lógica
        operaciones.put('~', "not"); // Operación lógica
    }

    /**
     * Método que escribe el código del comando push.
     * @param segment
     * @param index
     * @return String Código en lenguaje de máquina virtual del comando push.
     */
    public static String writePush(String segment, int index) {
        return "push " + segment + " " + index + "\n";
    }

    /**
     * Método que escribe el código del comando pop.
     * @param segment
     * @param index
     * @return String Código en lenguaje de máquina virtual del comando pop.
     */
    public static String writePop(String segment, int index) {
        return "pop " + segment + " " + index + "\n";
    }

    /**
     * Método que escribe el código del comando de operaciones aritméticas,
     * condicionales o lógicas.
     * @param operador
     * @return String Código en lenguaje de máquina virtual del comando seleccionado.
     */
    public static String writeArithmetic(char operador) {
        return operaciones.get(operador) + "\n";
    }

    /**
     * Método que escribe el código del comando label.
     * @param name
     * @return String Código en lenguaje de máquina virtual del comando label.
     */
    public static String writeLabel(String name) {
        return "label " + name + "\n"; 
    }

    /**
     * Método que escribe el código del comando goto.
     * @param label
     * @return String Código en lenguaje de máquina virtual del comando goto.
     */
    public static String writeGoto(String label) {
        return "goto " + label + "\n"; 
    }

    /**
     * Método que escribe el código del comando if-goto.
     * @param label
     * @return String Código en lenguaje de máquina virtual del comando if-goto.
     */
    public static String writeIf(String label) {
        return "if-goto " + label + "\n"; 
    }

    /**
     * Método que escribe el código del comando call.
     * @param function
     * @param nargs
     * @return String Código en lenguaje de máquina virtual del comando call.
     */
    public static String writeCall(String function, int nargs) {
        return "call " + function + " " + nargs + "\n"; 
    }

    /**
     * Método que escribe el código del comando function.
     * @param functionName
     * @param nlocals
     * @return String Código en lenguaje de máquina virtual del comando function.
     */
    public static String writeFunction(String functionName, int nlocals) {
        return "function " + functionName + " " + nlocals + "\n"; 
    }

    /**
     * Método que escribe el código del comando return.
     * @return String Código en lenguaje de máquina virtual del comando return.
     */
    public static String writeReturn() {
        return "return" + "\n";
    }
    
}