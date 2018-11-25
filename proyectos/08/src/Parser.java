import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

public class Parser {
    public static final int C_ARITHMETIC = 0;
    public static final int C_PUSH = 1;
    public static final int C_POP = 2;
    public static final int C_LABEL = 3;
    public static final int C_GOTO = 4;
    public static final int C_IF = 5;
    public static final int C_FUNCTION = 6;
    public static final int C_RETURN = 7;
    public static final int C_CALL = 8;
    private Scanner leerComando;
    private String comandoActual;
    public static final ArrayList<String> listaComandos = new ArrayList<String>();
    private int tipoArg;
    private String arg1;
    private int arg2;

    static {
        listaComandos.add("add"); listaComandos.add("sub"); listaComandos.add("neg"); // Comandos Aritmeticos/Booleanos
        listaComandos.add("eq"); listaComandos.add("gt"); listaComandos.add("lt"); // Comandos Condicionales
        listaComandos.add("and"); listaComandos.add("or"); listaComandos.add("not"); // Comandos Logicos
    }

    // Se lee el archivo, se procesa linea por linea definiendos los argumentos,
    // eliminando los comentarios y los espacios en blanco
    public Parser(File entradaArchivo) {
        tipoArg = -1;
        arg1 = "";
        arg2 = -1;
        try{
            leerComando = new Scanner(entradaArchivo);
            String preprocesada = "";
            String linea = "";
            while(leerComando.hasNext()){
                linea = eliminarComentarios(leerComando.nextLine()).trim();
                if (linea.length() > 0) {
                    preprocesada += linea + "\n";
                }
            }
            leerComando = new Scanner(preprocesada.trim());
        }catch (FileNotFoundException e) {
            System.out.println("ERROR: Archivo no encontrado");
        }
    }

    // Se obtiene el valor del argumento 1 que es aritmetico/booleano ó return
    public String arg1(){
            if(commandType() != C_RETURN){
                return arg1;
            }else{
                throw new IllegalStateException("ERROR: Argumento 1 no se obtuvo");
            }
    }

    // Se obtiene el valor del argumento 2 en caso de ser push o pop
    public int arg2(){
        if (commandType() == C_PUSH || commandType() == C_POP || commandType() == C_FUNCTION || commandType() == C_CALL){
            return arg2;
        }else {
            throw new IllegalStateException("ERROR: Argumento 2 no se obtuvo");
        }
    }

    // Método que elimina todos los comentarios (//) que existan en los archivos .vm
    public static String eliminarComentarios(String comentario){
        int posicion = comentario.indexOf("//");
        if (posicion != -1){
            comentario = comentario.substring(0, posicion);
        }
        return comentario;
    }

    // Método que permite analizar línea por línea el archivo .vm
    public void advance(){
        comandoActual = leerComando.nextLine();
        arg1 = "";
        arg2 = -1;
        String[] listaArgumentos = comandoActual.split(" ");
        if (listaArgumentos.length > 3){
            throw new IllegalArgumentException("ERROR: Argumentos no validos");
        }
        if (listaComandos.contains(listaArgumentos[0])){
            tipoArg = C_ARITHMETIC;
            arg1 = listaArgumentos[0];
        }else if(listaArgumentos[0].equals("return")){
            tipoArg = C_RETURN;
            arg1 = listaArgumentos[0];
        }else{
            arg1 = listaArgumentos[1];
            if(listaArgumentos[0].equals("push")){
                tipoArg = C_PUSH;
            }else if(listaArgumentos[0].equals("pop")){
                tipoArg = C_POP;
            }else if(listaArgumentos[0].equals("label")){
                tipoArg = C_LABEL;
            }else if(listaArgumentos[0].equals("if-goto")){
                tipoArg = C_IF;
            }else if (listaArgumentos[0].equals("goto")){
                tipoArg = C_GOTO;
            }else if (listaArgumentos[0].equals("function")){
                tipoArg = C_FUNCTION;
            }else if (listaArgumentos[0].equals("call")){
                tipoArg = C_CALL;
            }else {
                throw new IllegalArgumentException("ERROR: Comando no reconocido");
            }
            if (tipoArg == C_PUSH || tipoArg == C_POP  || tipoArg == C_FUNCTION || tipoArg == C_CALL){
                try {
                    arg2 = Integer.parseInt(listaArgumentos[2]);
                }catch (Exception e){
                    throw new IllegalArgumentException("ERROR: Segundo argumento no es un entero");
                }
            }
        }
    }

    // Método que elimina todos los espacios que existan en los archivos .vm
    public static String eliminarEspacios(String st){
        String strSinEspacios = "";
        if (st.length() != 0){
            String[] espacios = st.split(" ");
            for (String s: espacios){
                strSinEspacios += s;
            }
        }
        return strSinEspacios;
    }

    // Método que identifica el tipo de comando y retorna el número correspondiente a su tipo
    public int commandType(){
        if(tipoArg != -1){
            return tipoArg;
        }else{
            throw new IllegalStateException("ERROR: Comando no existente");
        }
    }

    // Método que permite obtener la extensión del archivo ingresado en los argumentos
    public static String getExtension(String nombreArchivo){
        int posicion = nombreArchivo.lastIndexOf('.');
        if (posicion != -1){
            return nombreArchivo.substring(posicion);
        }else {
            return "";
        }
    }

    // Método que verifica si existen más comandos por leer en los archivos .vm
    public boolean hasMoreCommands(){
        return leerComando.hasNextLine();
    }

}