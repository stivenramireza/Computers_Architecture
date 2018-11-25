import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeWriter {
    private int counterSaltos;
    private PrintWriter printArchivoSalida;
    private static int counterLabels = 0;
    private static String nombreArchivo = "";
    private static final Pattern labelRegex = Pattern.compile("^[^0-9][0-9A-Za-z\\_\\:\\.\\$]+");

    public CodeWriter(File archivoSalida) {
        try {
            nombreArchivo = archivoSalida.getName();
            printArchivoSalida = new PrintWriter(archivoSalida);
            counterSaltos = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método que genera el código en Assembler de RETURN
    public String generarCodigoReturn(){
        return "@LCL\n" +
                "D=M\n" +
                "@R11\n" +
                "M=D\n" +
                "@5\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@R12\n" +
                "M=D\n" +
                generarCodigoPop("ARG", 0, false) +
                "@ARG\n" +
                "D=M\n" +
                "@SP\n" +
                "M=D+1\n" +
                generarCodigoFrame("THAT") +
                generarCodigoFrame("THIS") +
                generarCodigoFrame("ARG") +
                generarCodigoFrame("LCL") +
                "@R12\n" +
                "A=M\n" +
                "0;JMP\n";
    }

    // Método que genera el código en Assembler para el frame (variable temporal) en el CALL
    public String generarCodigoFrame(String index){
        return "@R11\n" +
                "D=M-1\n" +
                "AM=D\n" +
                "D=M\n" +
                "@" + index + "\n" +
                "M=D\n";
    }

    // Método que genera el codigo de PUSH con el puntero y el temp
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

    // Método que genera el codigo en Assembler de POP con el puntero y el temp
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
    public void setFileName(File archivoSalida){
        nombreArchivo = archivoSalida.getName();
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

    // Método que genera código para los comandos que tengan PUSH y POP
    public void writePushPop(int comando, String segmento, int index){
        if (comando == Parser.C_PUSH){
            if (segmento.equals("constant")){
                printArchivoSalida.print("@" + index + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
            }else if (segmento.equals("static")){
                printArchivoSalida.print("@" + nombreArchivo + index + "\n" + "D=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
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
                printArchivoSalida.print("@" + nombreArchivo + index + "\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D\n");
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

    // Método que inicializa el programa
    public void writeInit(){
        printArchivoSalida.print("@256\n" +
                         "D=A\n" +
                         "@SP\n" +
                         "M=D\n");
        writeCall("Sys.init", 0);
    }

    // Método que genera código Assembler para las etiquetas
    public void writeLabel(String label){
        Matcher matcher = labelRegex.matcher(label);
        if (matcher.find()){
            printArchivoSalida.print("(" + label +")\n");
        }else {
            throw new IllegalArgumentException("ERROR: Label incorrecto");
        }
    }

    // Método que genera código Assembler para los saltos de goto
    public void writeGoto(String label){
        Matcher matcher = labelRegex.matcher(label);
        if (matcher.find()){
            printArchivoSalida.print("@" + label +"\n0;JMP\n");
        }else {
            throw new IllegalArgumentException("ERROR: El goto es incorrecto");
        }
    }

    // Método que genera código Assembler para los if-goto
    public void writeIf(String label){
        Matcher matcher = labelRegex.matcher(label);
        if (matcher.find()){
            printArchivoSalida.print(generarCodigoAritmetico() + "@" + label +"\nD;JNE\n");
        }else {
            throw new IllegalArgumentException("ERROR: El if-goto es incorrecto");
        }
    }

    // Método que genera código Assembler cuando se hace un llamado a una función
    public void writeCall(String functionName, int numArgs){
        String newLabel = "RETURN_LABEL" + (counterLabels++);
        printArchivoSalida.print("@" + newLabel + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        printArchivoSalida.print(generarCodigoPush("LCL", 0, true));
        printArchivoSalida.print(generarCodigoPush("ARG", 0, true));
        printArchivoSalida.print(generarCodigoPush("THIS", 0, true));
        printArchivoSalida.print(generarCodigoPush("THAT", 0, true));
        printArchivoSalida.print("@SP\n" +
                        "D=M\n" +
                        "@5\n" +
                        "D=D-A\n" +
                        "@" + numArgs + "\n" +
                        "D=D-A\n" +
                        "@ARG\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "D=M\n" +
                        "@LCL\n" +
                        "M=D\n" +
                        "@" + functionName + "\n" +
                        "0;JMP\n" +
                        "(" + newLabel + ")\n");
    }

    // Método que genera código Assembler cuando se retorna una función
    public void writeReturn(){
        printArchivoSalida.print(generarCodigoReturn());
    }

    // Método que genera código Assembler al crear las funciones
    public void writeFunction(String functionName, int numLocals){
        printArchivoSalida.print("(" + functionName +")\n");
        for (int i = 0; i < numLocals; i++){
            writePushPop(Parser.C_PUSH, "constant", 0);
        }
    }

    // Método para cerrar el archivo .asm después de generado
    public void close(){
        printArchivoSalida.close();
    }

}
