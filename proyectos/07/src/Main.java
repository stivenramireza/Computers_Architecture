import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("ERROR: Parametro invalido");
        }else{
            String nombreArchivo = args[0].substring(0, args[0].indexOf('.'));
            String salidaArchivo = nombreArchivo + ".asm";
            File inArchivo = new File(args[0]);
            File outArchivo = new File(salidaArchivo);
            CodeWriter generadorCodigo = new CodeWriter(outArchivo);
            Parser parser = new Parser(inArchivo);
            int tipoComando = -1;
            // Se analiza el archivo con el Parser
            while (parser.hasMoreCommands()) {
                parser.advance();
                tipoComando = parser.commandType();
                if (tipoComando == Parser.C_ARITHMETIC) {
                    generadorCodigo.writeArithmetic(parser.arg1());
                } else if (tipoComando == Parser.C_POP || tipoComando == Parser.C_PUSH) {
                    generadorCodigo.writePushPop(tipoComando, parser.arg1(), parser.arg2());
                }   
            }   
            generadorCodigo.close();
        }
    }
}