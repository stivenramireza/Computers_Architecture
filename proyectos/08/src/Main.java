import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.*;
import java.io.File;
import java.io.FilenameFilter;

public class Main {

    private BufferedReader lector;
	private FileWriter escritor;
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("ERROR: Parametro invalido" + "\n" +
                                "Uso: java Main [nombreArchivo | directorio]");
        }else{
            String archivoEntrada = args[0];
            File inArchivo = new File(archivoEntrada);
            String rutaArchivo = "";
            File archivoSalida;
            CodeWriter generadorCodigo;
            ArrayList<File> archivosVM = new ArrayList<File>();
            if (inArchivo.isFile()) {
                // Verifica que si hay un archivo sin directorio, efectivamente este tenga extension .vm
                String ruta = inArchivo.getAbsolutePath();
                if (!Parser.getExtension(ruta).equals(".vm")) {
                    throw new IllegalArgumentException("ERROR: Se requiere un archivo .vm");
                }
                archivosVM.add(inArchivo);
                rutaArchivo = inArchivo.getAbsolutePath().substring(0, inArchivo.getAbsolutePath().lastIndexOf(".")) + ".asm";
            } else if (inArchivo.isDirectory()) {
                // Obtiene todos los archivos .vm que hayan en el directorio
                archivosVM = obtenerArchivosVM(inArchivo);
                // Verifica si no existen archivos .vm en el directorio
                if (archivosVM.size() == 0) {
                    throw new IllegalArgumentException("ERROR: No existen archivos .vm en este directorio");
                }
                rutaArchivo = inArchivo.getAbsolutePath() + "/" +  inArchivo.getName() + ".asm";
            }
            archivoSalida = new File(rutaArchivo);
            if(archivoSalida.exists()) archivoSalida.delete();
            generadorCodigo = new CodeWriter(archivoSalida);
            for (File f : archivosVM) {
                String filename= f.getName().toString();
                filename=filename.substring(0,filename.indexOf(".vm"));
                if(filename.equals("Sys")){
                    generadorCodigo.writeInit();
                }
                Parser parser = new Parser(f);
                generadorCodigo.setFileName(f);
                int tipoComando = -1;
                // Se comienza a parsear el(los) archivo(s)
                while (parser.hasMoreCommands()) {
                    parser.advance();
                    tipoComando = parser.commandType();
                    if (tipoComando == Parser.C_ARITHMETIC) {
                        generadorCodigo.writeArithmetic(parser.arg1());
                    } else if (tipoComando == Parser.C_POP || tipoComando == Parser.C_PUSH) {
                        generadorCodigo.writePushPop(tipoComando, parser.arg1(), parser.arg2());
                    }  else if (tipoComando == Parser.C_RETURN) {
                        generadorCodigo.writeReturn();
                    } else if (tipoComando == Parser.C_FUNCTION) {
                        generadorCodigo.writeFunction(parser.arg1(),parser.arg2());
                    } else if (tipoComando == Parser.C_CALL) {
                        generadorCodigo.writeCall(parser.arg1(),parser.arg2());
                    } else if (tipoComando == Parser.C_LABEL) {
                        generadorCodigo.writeLabel(parser.arg1());
                    } else if (tipoComando == Parser.C_GOTO) {
                        generadorCodigo.writeGoto(parser.arg1());
                    } else if (tipoComando == Parser.C_IF) {
                        generadorCodigo.writeIf(parser.arg1());
                    }
                }
            }
            generadorCodigo.close();
        }
    }

    // Ingresa a un directorio y busca los archivos que terminen en .vm
    // para guardarlo en un ArrayList y con este compilarlos
    public static ArrayList<File> obtenerArchivosVM(File direccion){
        File[] archivos = direccion.listFiles();
        ArrayList<File> listaArchivos = new ArrayList<File>();
        for (File f:archivos){
            if (f.getName().endsWith(".vm")){
                listaArchivos.add(f);
            }
        }
        return listaArchivos;
    }
}
