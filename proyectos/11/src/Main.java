import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
/**
 * Esta clase permite ejecutar el compilador 2 (Code Generation).
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class Main{
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ERROR: Parametro invalido" + "\n" +
                                "Uso: java Main [nombreArchivo | directorio]");
            System.exit(1);
        }else{
            String archivoEntrada = args[0];
            File inArchivo = new File(archivoEntrada);
            String rutaArchivo = "";
            File archivoSalida;
            ArrayList<File> archivosJack = new ArrayList<File>();
            if (inArchivo.isFile()) {
                // Verifica que si hay un archivo sin directorio, efectivamente este tenga extension .jack
                String ruta = inArchivo.getAbsolutePath();
                if (!CompilationEngine.getExtension(ruta).equals(".jack")) {
                    throw new IllegalArgumentException("ERROR: Se requiere un archivo .jack");
                }
                archivosJack.add(inArchivo);
                recorrerDirectorio(archivosJack);
            } else if (inArchivo.isDirectory()) {
                // Obtiene todos los archivos .jack que hayan en el directorio
                archivosJack = obtenerArchivosJack(inArchivo);
                // Verifica si no existen archivos .jack en el directorio
                if (archivosJack.size() == 0) {
                    throw new IllegalArgumentException("ERROR: No existen archivos .jack en este directorio");
                }else{
                    recorrerDirectorio(archivosJack);
                }
            }
        }
    }

    /**
     * Método que permite traducir los archivos con el Parser
     * 
     * @param nombreArchivo
     * @throws Exception
     */
    private static void traducirArchivos(String nombreArchivo) throws Exception {
        // Recorre todo el árbol abstracto sintáctico.
        JackGrammarLexer lexer1 = new JackGrammarLexer(CharStreams.fromFileName(nombreArchivo));
        CommonTokenStream tokens = new CommonTokenStream(lexer1);
        JackGrammarParser parser = new JackGrammarParser(tokens);
        ParseTree tree = parser.classJack();
        // Traduce el archivo VMParser.
        JackGrammarLexer lexer2 = new JackGrammarLexer(CharStreams.fromFileName(nombreArchivo));
        CompilationEngine traductorParser = new CompilationEngine(lexer2);
        String VMParser = traductorParser.visit(tree);
        traducirArchivo(nombreArchivo, VMParser, "");  
    }

    /**
     * Método que permite escribir el archivo VM con su respectiva extensión y con los archivos
     * traducidos con extensión .jack.
     * 
     * @param nombreArchivo
     * @param textVM
     * @param ext
     */
    private static void traducirArchivo(String nombreArchivo, String textVM, String ext) {
        try {
            nombreArchivo = nombreArchivo.substring(0, nombreArchivo.length()-5);
            PrintWriter writer = new PrintWriter(nombreArchivo + ext + ".vm", "UTF-8");
            writer.print(textVM);
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR: No es posible crear el fichero.");
            System.exit(1);
        }
    }

    /**
     * Método que ingresa a un directorio y busca los archivos que terminen en .jack
     * para guardarlo en un ArrayList y con este compilarlos.
     * 
     * @param direccion
     * @return listaArchivos Lista con los archivos .jack
     */
    public static ArrayList<File> obtenerArchivosJack(File direccion){
        File[] archivos = direccion.listFiles();
        ArrayList<File> listaArchivos = new ArrayList<File>();
        for (File f:archivos){
            if (f.getName().endsWith(".jack")){
                listaArchivos.add(f);
            }
        }
        return listaArchivos;
    }

    /**
     * Método que ingresa al directorio, lo recorrer y traduce los archivos .jack
     * al formato .vm (lenguaje de máquina virtual de Jack).
     * 
     * @param archivosJack
     */
    private static void recorrerDirectorio(ArrayList<File> archivosJack) {
        for (int i = 0; i < archivosJack.size(); i++) {
            try {
                traducirArchivos(archivosJack.get(i).toString());
            } catch (Exception error) {
                System.err.println("ERROR: " + error);
                System.exit(1);
            }
        }
    }
}