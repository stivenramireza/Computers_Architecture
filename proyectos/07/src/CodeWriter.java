import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeWriter {
    private int counterSaltos;
    private PrintWriter printArchivoSalida;

    public CodeWriter(File archivoSalida) {
        try {
            printArchivoSalida = new PrintWriter(archivoSalida);
            counterSaltos = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Este método genera el codigo de PUSH con el puntero y el temp
    private String generarCodigoPush(String memorySegment, int index, boolean esDirecto){
        String pointerC = "";
        if(esDirecto){
            pointerC = "";
        }else{
            pointerC = "@" + index + "\n" + "A=D+A\nD=M\n";
        }
        return "@" + memorySegment + "\n" +
                "D=M\n"+
                pointerC +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";
    }

    // Este método genera el codigo de POP con el puntero y el temp
    private String generarCodigoPop(String memorySegment, int index, boolean esDirecto){
        String pointerC = "";
        if(esDirecto){
            pointerC = "D=A\n";
        }else{
            pointerC = "D=M\n@" + index + "\nD=D+A\n";
        }
        return "@" + memorySegment + "\n" +
                pointerC +
                "@R13\n" +
                "M=D\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@R13\n" +
                "A=M\n" +
                "M=D\n";
    }

    // Metodo que genera codigo en Assembler para los comandos ADD y SUB
    private String generarCodigoAritmetico(){
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n";
    }

    // Metodo que genera codigo en Assembler para los comandos EQ (JNE), LT(JGE), GT(JLE)
    private String generarCodigoCondicional(String salto){
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@CONDICION" + counterSaltos + "\n" +
                "D;" + salto + "\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=-1\n" +
                "@END" + counterSaltos + "\n" +
                "0;JMP\n" +
                "(CONDICION" + counterSaltos + ")\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=0\n" +
                "(END" + counterSaltos + ")\n";
    }

    // Metodo que retorna el nombre del archivo procesado
    public String setFileName(String nombreArchivo){
        return nombreArchivo;
    }

    // Metodo que identifica el comando y genera el codigo en Assembler para
    // los comandos aritmeticos/booleanos y logicos (por el momento)
    public void writeArithmetic(String comando){
        if (comando.equals("add")){
            printArchivoSalida.print(generarCodigoAritmetico() + "M=M+D\n");
        }else if (comando.equals("sub")){
            printArchivoSalida.print(generarCodigoAritmetico() + "M=M-D\n");
        }else if (comando.equals("neg")){
            printArchivoSalida.print("D=0\n@SP\nA=M-1\nM=D-M\n");
        }else if (comando.equals("and")){
            printArchivoSalida.print(generarCodigoAritmetico() + "M=M&D\n");
        }else if (comando.equals("or")){
            printArchivoSalida.print(generarCodigoAritmetico() + "M=M|D\n");
        }else if (comando.equals("not")){
            printArchivoSalida.print("@SP\nA=M-1\nM=!M\n");
        }else if (comando.equals("eq")){
            printArchivoSalida.print(generarCodigoCondicional("JNE"));
            counterSaltos++;
        }else if (comando.equals("lt")){
            printArchivoSalida.print(generarCodigoCondicional("JGE"));
            counterSaltos++;
        }else if (comando.equals("gt")){
            printArchivoSalida.print(generarCodigoCondicional("JLE"));
            counterSaltos++;
        }else {
            throw new IllegalArgumentException("ERROR: No se pudo procesar el comando");
        }
    }

    public void writePushPop(int comando, String segmento, int index){
        if (comando == Parser.C_PUSH){
            if (segmento.equals("constant")){
                printArchivoSalida.print("@" + index + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
            }else if (segmento.equals("static")){
                printArchivoSalida.print(generarCodigoPush(String.valueOf(16 + index), index, false));
            }else if (segmento.equals("local")){
                printArchivoSalida.print(generarCodigoPush("LCL", index, false));
            }else if (segmento.equals("argument")){
                printArchivoSalida.print(generarCodigoPush("ARG", index, false));
            }else if (segmento.equals("this")){
                printArchivoSalida.print(generarCodigoPush("THIS", index, false));
            }else if (segmento.equals("that")){
                printArchivoSalida.print(generarCodigoPush("THAT", index, false));
            }else if (segmento.equals("temp")){
                printArchivoSalida.print(generarCodigoPush("R5", index + 5, false));
            }else if (segmento.equals("pointer") && index == 0){
                printArchivoSalida.print(generarCodigoPush("THIS",index, true));
            }else if (segmento.equals("pointer") && index == 1){
                printArchivoSalida.print(generarCodigoPush("THAT",index, true));
            }
        }else if(comando == Parser.C_POP){
            if (segmento.equals("static")){
                printArchivoSalida.print(generarCodigoPop(String.valueOf(16 + index), index, false));
            }else if (segmento.equals("local")){
                printArchivoSalida.print(generarCodigoPop("LCL", index, false));
            }else if (segmento.equals("argument")){
                printArchivoSalida.print(generarCodigoPop("ARG", index, false));
            }else if (segmento.equals("this")){
                printArchivoSalida.print(generarCodigoPop("THIS", index, false));
            }else if (segmento.equals("that")){
                printArchivoSalida.print(generarCodigoPop("THAT", index, false));
            }else if (segmento.equals("temp")){
                printArchivoSalida.print(generarCodigoPop("R5", index + 5, false));
            }else if (segmento.equals("pointer") && index == 0){
                printArchivoSalida.print(generarCodigoPop("THIS",index, true));
            }else if (segmento.equals("pointer") && index == 1){
                printArchivoSalida.print(generarCodigoPop("THAT",index, true));
            }
        }else {
            throw new IllegalArgumentException("ERROR: No se pudo procesar el comando PUSH | POP");
        }
    }

    public void close(){
        printArchivoSalida.close();
    }

}