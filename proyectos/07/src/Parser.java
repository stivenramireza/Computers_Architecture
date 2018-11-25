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

    // Se obtiene el valor del argumento 1 que es aritmetico/booleano
    public String arg1(){
            return arg1;
    }

    // Se obtiene el valor del argumento 2 en caso de ser push o pop
    public int arg2(){
        if (commandType() == C_PUSH || commandType() == C_POP){
            return arg2;
        }else {
            throw new IllegalStateException("ERROR: Argumento 2 no se obtuvo");
        }
    }

    public static String eliminarComentarios(String comentario){
        int posicion = comentario.indexOf("//");
        if (posicion != -1){
            comentario = comentario.substring(0, posicion);
        }
        return comentario;
    }

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
        }else{
            arg1 = listaArgumentos[1];
            if(listaArgumentos[0].equals("push")){
                tipoArg = C_PUSH;
            }else if(listaArgumentos[0].equals("pop")){
                tipoArg = C_POP;
            }else {
                throw new IllegalArgumentException("ERROR: Comando no reconocido");
            }
            if (tipoArg == C_PUSH || tipoArg == C_POP ){
                try {
                    arg2 = Integer.parseInt(listaArgumentos[2]);
                }catch (Exception e){
                    throw new IllegalArgumentException("ERROR: Segundo argumento no es un entero");
                }
            }
        }
    }

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

    public int commandType(){
        if(tipoArg != -1){
            return tipoArg;
        }else{
            throw new IllegalStateException("ERROR: Comando no existente");
        }
    }

    public static String getExtension(String nombreArchivo){
        int posicion = nombreArchivo.lastIndexOf('.');
        if (posicion != -1){
            return nombreArchivo.substring(posicion);
        }else {
            return "";
        }
    }

    public boolean hasMoreCommands(){
        return leerComando.hasNextLine();
    }

}