import org.antlr.v4.runtime.*;
import java.util.HashMap;
/**
 * Esta clase permite recorrer el parser generado por la gramática y a partir de él,
 * identificar los elementos que se encuentran en los archivos .jack para luego
 * pasarlo a lenguaje de máquina virtual.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class CompilationEngine extends JackGrammarBaseVisitor<String>{
    private JackGrammarLexer lexer;
    private Token token;
    private int tokenType, eof;
    private boolean foundEof;
    private String claseActual;
    private GlobalTable tablaGlobal;
    private LocalTable tablaLocal;
    private HashMap<String, Integer> subrutinas;
    private int contadorLabel, callArgs;

    /**
     * Constructor de la clase CompilationEngine.
     * 
     * @param lexer
     */
    public CompilationEngine(JackGrammarLexer lexer) {
        super();
        this.lexer = lexer;
        token = lexer.nextToken();
        Token eofToken = lexer.emitEOF();
        eof = eofToken.getType();
        tokenType = token.getType();
        foundEof = false;
        claseActual = "";
        contadorLabel = 0;
    }

    /**
     * Método que permite avanzar en el recorrido del archivo .jack, identificando
     * cada uno de los tokens.
     */
    private void advance() {
        if (tokenType != eof) {
            token = lexer.nextToken();
            tokenType = token.getType();
        } else {
            System.err.println("ERROR: Fin de fichero. No hay más tokens.");
            System.exit(1);
        }        
    }

    /**
     * Método que permite obtener el texto del token y avanzar en el recorrido
     * de la gramática.
     * @return text Texto del token.
     */
    private String obtenerTexto() {
        String text = token.getText();
        advance();
        return text;
    }

    /**
     * Método que permite revisar las variables en el archivo .jack y verificar si
     * estos se encuentran ya registrados en la tabla global o en la tabla local.
     * @param nombreVariable
     */
    private void revisarVariable(String nombreVariable) {
        if (!tablaGlobal.contains(nombreVariable) && !tablaLocal.contains(nombreVariable)) {
            System.err.println("ERROR: Variable no declarada: " + nombreVariable + "Clase: " + claseActual + ".java") ;
            System.exit(1);
        }
    }

    /**
     * Método que permite obtener la tabla global si el nombre de la variable se encuentra
     * registrada. De lo contrario, se obtiene la tabla local.
     * @param nombreVariable
     * @return tablaSimbolos Tabla global o local según sea el caso.
     */
    private SymbolTable getTable(String nombreVariable) {
        if (tablaGlobal.contains(nombreVariable)) {
            return tablaGlobal;
        }
        return tablaLocal;
    }

    /**
     * Método que permite crear etiquetas en caso de requerirlo.
     * @return label Etiqueta seleccionada.
     */
    private String crearLabel() {
        String label = "Label" + contadorLabel;
        contadorLabel++;
        return label;
    }

    /**
     * Método que compila la regla de la gramática classJack y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassJack(JackGrammarParser.ClassJackContext contexto) {
        subrutinas = new HashMap<String, Integer>();
        tablaGlobal = new GlobalTable();
        String VM_Code = "";
        advance(); // class
        claseActual = visit(contexto.className());
        advance(); // {
        for (JackGrammarParser.ClassVarDecContext c: contexto.classVarDec()) {
            VM_Code += visit(c);            
        } 
        for (JackGrammarParser.SubroutineDecContext c: contexto.subroutineDec()) {
            VM_Code += visit(c);            
        }
        advance(); // }
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática classVarDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassVarDec(JackGrammarParser.ClassVarDecContext contexto) {
        String VM_Code = "";
        String varSegmento = obtenerTexto();
        String varTipo = visit(contexto.type());
        String varNombre = visit(contexto.varName(0));
        tablaGlobal.agregarSimbolo(varNombre, varTipo, varSegmento);  
        int tamanoLista = contexto.varName().size();
        for (int i = 1; i < tamanoLista; ++i) {
            advance();  // ,
            varNombre = visit(contexto.varName(i));            
            tablaGlobal.agregarSimbolo(varNombre, varTipo, varSegmento);
        }
        advance(); // ;
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "int".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitEntero(JackGrammarParser.EnteroContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila el literal de la gramática "char".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitCaracter(JackGrammarParser.CaracterContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila el literal de la gramática "boolean".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitBooleano(JackGrammarParser.BooleanoContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila la regla de la gramática type (className) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitNameClase(JackGrammarParser.NameClaseContext contexto) {
        String VM_Code = visit(contexto.className());
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática subroutineDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineDec(JackGrammarParser.SubroutineDecContext contexto) {
        tablaLocal = new LocalTable();
        String VM_Code = "";
        String tipoSubrutina = obtenerTexto(); // constructor | function | method
        String tipoRetorno = "";
        if (contexto.type() == null) {
            tipoRetorno = obtenerTexto();
        } else {
            tipoRetorno = visit(contexto.type());
        }
        String nombreSubrutina = visit(contexto.subroutineName());
        String temp = "";
        if (tipoSubrutina.equals("method")) {
            tablaLocal.agregarSimbolo("this", claseActual, "argument");
            temp = VMWriter.writePush("argument", 0);
            temp += VMWriter.writePop("pointer", 0);
        }
        
        advance(); // (
        VM_Code += visit(contexto.parameterList());
        advance(); // )
        temp += visit(contexto.subroutineBody());
        int numVarLocales = tablaLocal.numeroVars();
        
        VM_Code += VMWriter.writeFunction(claseActual + "." + nombreSubrutina, numVarLocales);
        if (tipoSubrutina.equals("constructor")) {
            if (!tipoRetorno.equals(claseActual)) {
                System.err.println("ERROR: El tipo de dato de retorno del constructor debe ser de la clase.");
                System.exit(1);
            }
            if (!nombreSubrutina.equals("new")) {
                System.err.println("ERROR: El nombre del constructor debe ser \"new\"");
                System.exit(1);
            }
            int numFields = tablaGlobal.numeroFields();
            VM_Code += VMWriter.writePush("constant", numFields);
            VM_Code += VMWriter.writeCall("Memory.alloc", 1);
            VM_Code += VMWriter.writePop("pointer", 0);
        }
        VM_Code += temp;
        int numArgs = tablaLocal.numeroVars();
        subrutinas.put(nombreSubrutina, numArgs);
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática parameterList y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitParameterList(JackGrammarParser.ParameterListContext contexto) {
        String VM_Code = "";
        if (!contexto.type().isEmpty()) {
            String tipoVar = visit(contexto.type(0));
            String nombreVar = visit(contexto.varName(0));
            tablaLocal.agregarSimbolo(nombreVar, tipoVar, "argument");
            int tamanoLista = contexto.type().size();
            for (int i = 1; i < tamanoLista; ++i) {
                advance(); // ,
                tipoVar = visit(contexto.type(i));
                nombreVar = visit(contexto.varName(i));
                tablaLocal.agregarSimbolo(nombreVar, tipoVar, "argument");
            }
        }        
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática subroutineBody y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineBody(JackGrammarParser.SubroutineBodyContext contexto) {
        String VM_Code = "";
        advance(); // {
        for (JackGrammarParser.VarDecContext c: contexto.varDec()) {
            VM_Code += visit(c);            
        }
        VM_Code += visit(contexto.statements());
        advance(); // }
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática varDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarDec(JackGrammarParser.VarDecContext contexto) {
        String VM_Code = "";
        advance(); // var
        String tipoVar = visit(contexto.type());
        String nombreVar = visit(contexto.varName(0));
        tablaLocal.agregarSimbolo(nombreVar, tipoVar, "var");
        int tamanoLista = contexto.varName().size();
        for (int i = 1; i < tamanoLista; ++i) {
            advance(); // ,
            nombreVar = visit(contexto.varName(i));
            tablaLocal.agregarSimbolo(nombreVar, tipoVar, "var");
        }
        advance(); // ;
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática className y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassName(JackGrammarParser.ClassNameContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila la regla de la gramática subroutineName y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineName(JackGrammarParser.SubroutineNameContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila la regla de la gramática varName y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarName(JackGrammarParser.VarNameContext contexto) {
        return obtenerTexto();
    }

    /**
     * Método que compila la regla de la gramática statements y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitStatements(JackGrammarParser.StatementsContext contexto) {
        String VM_Code = "";
        for (JackGrammarParser.StatementContext c: contexto.statement()) {
            VM_Code += visit(c);
        }
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática statement (opLet) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpLet(JackGrammarParser.OpLetContext contexto) {
        String VM_Code = ""; 
        VM_Code += visit(contexto.letStatement());
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática statement (opIf) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpIf(JackGrammarParser.OpIfContext contexto) {
        String VM_Code = "";
        VM_Code += visit(contexto.ifStatement());
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática statement (opWhile) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpWhile(JackGrammarParser.OpWhileContext contexto) {
        String VM_Code = "";
        VM_Code += visit(contexto.whileStatement());
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática statement (opDo) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpDo(JackGrammarParser.OpDoContext contexto) {
        String VM_Code = "";
        VM_Code += visit(contexto.doStatement());
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática statement (opReturn) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpReturn(JackGrammarParser.OpReturnContext contexto){ 
        String VM_Code = "";
        VM_Code += visit(contexto.returnStatement());
        return VM_Code;
    }
    
    /**
     * Método que compila la regla de la gramática letStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitLetStatement(JackGrammarParser.LetStatementContext contexto) {
        String VM_Code = "";
        advance(); // let
        String nombreVar = visit(contexto.varName());
        revisarVariable(nombreVar);
        SymbolTable tablaSimbolos = getTable(nombreVar);
        int contadorExp = 0;
        boolean esArray = false;
        if (token.getText().equals("[")) {
            esArray = true;
            advance(); // [
            VM_Code += visit(contexto.expression(contadorExp));
            VM_Code += VMWriter.writePush(tablaSimbolos.getVMSegmento(nombreVar), tablaSimbolos.getPosition(nombreVar));
            VM_Code += VMWriter.writeArithmetic('+');
            advance(); // ]
            contadorExp++;
        }
        advance(); // =
        VM_Code += visit(contexto.expression(contadorExp));
        if (esArray) {
            VM_Code += VMWriter.writePop("temp", 0);
            VM_Code += VMWriter.writePop("pointer", 1);
            VM_Code += VMWriter.writePush("temp", 0);
            VM_Code += VMWriter.writePop("that", 0);
        } else {
            VM_Code += VMWriter.writePop(tablaSimbolos.getVMSegmento(nombreVar), tablaSimbolos.getPosition(nombreVar));
        }

        advance(); // ;
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática ifStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitIfStatement(JackGrammarParser.IfStatementContext contexto) {
        String VM_Code = "";
        String falseLabel = crearLabel() + "_FALSE";
        String endIfLabel = crearLabel() + "_END_IF";
        advance(); // if
        advance(); // (
        VM_Code += visit(contexto.expression());
        advance(); // )
        VM_Code += VMWriter.writeArithmetic('~');
        VM_Code += VMWriter.writeIf(falseLabel);        
        advance(); // {
        VM_Code += visit(contexto.statements(0));
        advance(); // }
        VM_Code += VMWriter.writeGoto(endIfLabel);
        VM_Code += VMWriter.writeLabel(falseLabel);
        int tamanoLista = contexto.statements().size();
        if (tamanoLista > 1) {
            advance(); // else
            advance(); // {
            VM_Code += visit(contexto.statements(1));
            advance(); // }
        }
        VM_Code += VMWriter.writeLabel(endIfLabel);        
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática whileStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitWhileStatement(JackGrammarParser.WhileStatementContext contexto) {
        String VM_Code = "";
        String whileLabel = crearLabel() + "_WHILE";
        String endWhileLabel = crearLabel() + "_END_WHILE";
        advance(); // while
        VM_Code += VMWriter.writeLabel(whileLabel);
        advance(); // (
        VM_Code += visit(contexto.expression());
        VM_Code += VMWriter.writeArithmetic('~');
        VM_Code += VMWriter.writeIf(endWhileLabel);
        advance(); // )
        advance(); // {
        VM_Code += visit(contexto.statements());
        VM_Code += VMWriter.writeGoto(whileLabel);
        advance(); // }
        VM_Code += VMWriter.writeLabel(endWhileLabel);
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática doStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitDoStatement(JackGrammarParser.DoStatementContext contexto) {
        String VM_Code = "";       
        advance(); // do
        VM_Code += visit(contexto.subroutineCall());
        advance(); // ;
        VM_Code += VMWriter.writePop("temp", 0); 
        return VM_Code;  
    }

    /**
     * Método que compila la regla de la gramática returnStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitReturnStatement(JackGrammarParser.ReturnStatementContext contexto) {
        String VM_Code = "";
        advance(); // return
        if (contexto.expression() != null) {
            VM_Code += visit(contexto.expression());
        } else {
            VM_Code += VMWriter.writePush("constant", 0);
        }
        VM_Code += VMWriter.writeReturn();
        advance(); // ;
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática expression y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitExpression(JackGrammarParser.ExpressionContext contexto) {
        String VM_Code = "";
        VM_Code += visit(contexto.term(0));
        int tamanoLista = contexto.operator().size();
        for (int i = 0; i < tamanoLista; ++i) {
            String temp = visit(contexto.operator(i));
            VM_Code += visit(contexto.term(i+1)) + temp;
        }
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (INTEGERCONSTANT) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitIntConstant(JackGrammarParser.IntConstantContext contexto) {
        String VM_Code = "";
        int constanteInt = Integer.parseInt(obtenerTexto());        
        if (constanteInt > 32767) {
            System.err.println("ERROR: Valor numérico fuera del rango: " + constanteInt);
            System.exit(1);
        }
        VM_Code += VMWriter.writePush("constant", constanteInt);
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (STRINGCONSTANT) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitStringConstant(JackGrammarParser.StringConstantContext contexto) {
        String VM_Code = "";
        String str = obtenerTexto();
        VM_Code += VMWriter.writePush("constant", str.length()-2);
        VM_Code += VMWriter.writeCall("String.new", 1);
        for (int i = 1; i < str.length()-1; ++i) {
            VM_Code += VMWriter.writePush("constant", (int) str.charAt(i));
            VM_Code += VMWriter.writeCall("String.appendChar", 2);
        }
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (keyword_Constant) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitKeyword_Constant(JackGrammarParser.Keyword_ConstantContext contexto) {
        return visit(contexto.keywordConstant());
    }

    /**
     * Método que compila la regla de la gramática term (nombreVariable) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitNombreVariable(JackGrammarParser.NombreVariableContext contexto) {
        String VM_Code = "";
        String variable = visit(contexto.varName());
        revisarVariable(variable);
        SymbolTable tablaSimbolos = getTable(variable);
        VM_Code += VMWriter.writePush(tablaSimbolos.getVMSegmento(variable), tablaSimbolos.getPosition(variable));
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (varArreglo) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarArreglo(JackGrammarParser.VarArregloContext contexto) {
        String VM_Code = "";
        String variable = visit(contexto.varName());
        revisarVariable(variable);
        advance(); // [
        SymbolTable tablaSimbolos = getTable(variable);
        VM_Code += visit(contexto.expression());
        VM_Code += VMWriter.writePush(tablaSimbolos.getVMSegmento(variable), tablaSimbolos.getPosition(variable));
        VM_Code += VMWriter.writeArithmetic('+');
        VM_Code += VMWriter.writePop("pointer", 1);
        VM_Code += VMWriter.writePush("that", 0);
        advance(); // ]
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (subrutinaCall) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubrutinaCall(JackGrammarParser.SubrutinaCallContext contexto) {
        return visit(contexto.subroutineCall());
    }

    /**
     * Método que compila la regla de la gramática term (parentesis) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitParentesis(JackGrammarParser.ParentesisContext contexto) {
        String VM_Code = "";
        advance(); // (
        VM_Code += visit(contexto.expression());
        advance(); // )
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática term (unaryOperator) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */    
    public String visitUnaryOperator(JackGrammarParser.UnaryOperatorContext contexto) {
        String temp = visit(contexto.unaryOp());
        String VM_Code = visit(contexto.term()) + temp;
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática subroutineCall (llamadaSubrutina) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitLlamadaSubrutina(JackGrammarParser.LlamadaSubrutinaContext contexto) {
        String VM_Code = "";
        String nombreSubrutina = obtenerTexto();
        callArgs = 1;
        VM_Code += VMWriter.writePush("pointer", 0);
        advance(); // (
        VM_Code += visit(contexto.expressionList());
        advance(); // )
        VM_Code += VMWriter.writeCall(claseActual + "." + nombreSubrutina, callArgs);
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática subroutineCall (objectCall) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitLlamadaObjeto(JackGrammarParser.LlamadaObjetoContext contexto) {
        String VM_Code = "";
        String nombreObjeto = visit(contexto.className());
        SymbolTable tablaSimbolos = getTable(nombreObjeto);
        String nombreSubrutina = "";
        String nombreCall = "";
        callArgs = 0;
        if (!tablaSimbolos.contains(nombreObjeto)) {
            nombreCall = nombreObjeto;
        } else {
            revisarVariable(nombreObjeto);
            if (tablaSimbolos.esPrimitivo(nombreObjeto)) {
                System.err.println("ERROR: La variable " + nombreObjeto+ " no es un objeto.");
                System.exit(1);
            }
            VM_Code += VMWriter.writePush(tablaSimbolos.getVMSegmento(nombreObjeto), tablaSimbolos.getPosition(nombreObjeto));
            nombreCall = tablaSimbolos.getType(nombreObjeto);
            callArgs = 1;
        }
        advance(); // .
        nombreSubrutina = obtenerTexto();
        nombreCall += "." + nombreSubrutina;
        advance();  // (
        VM_Code += visit(contexto.expressionList());
        advance(); // )
        VM_Code += VMWriter.writeCall(nombreCall, callArgs);
        return VM_Code;
    }

    /**
     * Método que compila la regla de la gramática expressionList y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return VM_Code String que contiene las palabras claves y símbolos generados.
     */
    public String visitExpressionList(JackGrammarParser.ExpressionListContext contexto) {
        String VM_Code = "";
        if (!contexto.expression().isEmpty()) {
            VM_Code += visit(contexto.expression(0));
            int tamanoLista = contexto.expression().size();
            for (int i = 1; i < tamanoLista; ++i) {
                advance();
                VM_Code += visit(contexto.expression(i));
            }
            callArgs += tamanoLista;
        }        
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "+".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitSuma(JackGrammarParser.SumaContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('+');
        advance();
        return VM_Code;
    }
 
    /**
     * Método que compila el literal de la gramática "-".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitResta(JackGrammarParser.RestaContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('-');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "*".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitMult(JackGrammarParser.MultContext contexto) {
        String VM_Code = VMWriter.writeCall("Math.multiply", 2);
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "/".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitDivision(JackGrammarParser.DivisionContext contexto) {
        String VM_Code = VMWriter.writeCall("Math.divide", 2);
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "&".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitAnd(JackGrammarParser.AndContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('&');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "|".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitOr(JackGrammarParser.OrContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('|');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "<".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitMenor(JackGrammarParser.MenorContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('<');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática ">".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitMayor(JackGrammarParser.MayorContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('>');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "=".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitIgual(JackGrammarParser.IgualContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('=');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "-".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitNeg(JackGrammarParser.NegContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('n');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "~".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitNot(JackGrammarParser.NotContext contexto) {
        String VM_Code = VMWriter.writeArithmetic('~');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "true".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitTrue(JackGrammarParser.TrueContext contexto) {
        String VM_Code = VMWriter.writePush("constant", 0);
        VM_Code += VMWriter.writeArithmetic('~');
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "false".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitFalse(JackGrammarParser.FalseContext contexto) {
        String VM_Code = VMWriter.writePush("constant", 0);
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "null".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitNull(JackGrammarParser.NullContext contexto) {
        String VM_Code = VMWriter.writePush("constant", 0);
        advance();
        return VM_Code;
    }

    /**
     * Método que compila el literal de la gramática "this".
     * @param contexto
     * @return VM_Code String que contiene el literal generado.
     */
    public String visitThis(JackGrammarParser.ThisContext contexto) {
        String VM_Code = VMWriter.writePush("pointer", 0);
        advance();
        return VM_Code;
    }

    /**
     * Método que permite obtener la extensión del archivo ingresado en los argumentos.
     * @param nombreArchivo
     * @return String Extensión del archivo.
     */
    public static String getExtension(String nombreArchivo){
        int posicion = nombreArchivo.lastIndexOf('.');
        if (posicion != -1){
            return nombreArchivo.substring(posicion);
        }else {
            return "";
        }
    }
}