import org.antlr.v4.runtime.*;
/**
 * Esta clase permite recorrer el parser generado por la gramática y a partir de él,
 * identificar los elementos que se encuentran en el archivo .jack para luego
 * pasarlo a lenguaje de máquina virtual.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class TraductorParser extends JackGrammarBaseVisitor<String>{
    private JackGrammarLexer lexer;
    private Token token;
    int tokenType, eof;
    boolean foundEof;
    int contadorTabs;

    /**
     * Constructor de la clase TraductorParser.
     * 
     * @param lexer
     */
    public TraductorParser(JackGrammarLexer lexer) {
        super();
        this.lexer = lexer;
        token = lexer.nextToken();
        Token eofToken = lexer.emitEOF();
        eof = eofToken.getType();
        tokenType = token.getType();
        foundEof = false;
        contadorTabs = 0;
    }

    /**
     * Método que permite avanzar en el recorrido del archivo .jack, identificando
     * cada uno de los tokens.
     * 
     * @return XML Archivo generado en XML
     */
    public String advance() {
        String XML = "";
        if (tokenType != eof) {
            XML = TraductorTokens.tipoToken(tokenType, token);
            token = lexer.nextToken();
            tokenType = token.getType();
        } else {
            System.err.println("ERROR: Fin de fichero. No hay más tokens.");
            System.exit(1);
        }
        return XML;            
    }

    /**
     * Método que permite tabular el archivo a medida que se identifica un nuevo token.
     * 
     * @param s
     * @return resultado Tabulación generada.
     */
    public String tabular(String s) {
        String resultado = "";
        String tabulador = "   ";
        for (int i = 0; i < contadorTabs; ++i) {
            resultado += tabulador;
        }
        return resultado + s;
    }

    /**
     * Método que compila la regla de la gramática classJack y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassJack(JackGrammarParser.ClassJackContext contexto) {
        String XML = "<class>" + "\n";
        ++contadorTabs;
        XML += tabular(advance()); // class
        XML += visit(contexto.className());
        XML += tabular(advance()); // {
        for (JackGrammarParser.ClassVarDecContext c: contexto.classVarDec()) {
            XML += visit(c);            
        } 
        for (JackGrammarParser.SubroutineDecContext c: contexto.subroutineDec()) {
            XML += visit(c);            
        }
        XML += tabular(advance()); // }
        --contadorTabs;
        XML += "</class>" + "\n";
        return XML;
    }

    /**
     * Método que compila la regla de la gramática classVarDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassVarDec(JackGrammarParser.ClassVarDecContext contexto) {
        String XML = tabular("<classVarDec>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // static | field
        XML += visit(contexto.type()) + visit(contexto.varName(0));
        int tamanoLista = contexto.varName().size();
        for (int i = 1; i < tamanoLista; ++i) {
            XML += tabular(advance()); // ,
            XML += visit(contexto.varName(i));
        }
        XML += tabular(advance()); // ;
        --contadorTabs;
        XML += tabular("</classVarDec>" + "\n");
        return XML;
    }

    /**
     * Método que compila el literal de la gramática "int".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitEntero(JackGrammarParser.EnteroContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "char".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitCaracter(JackGrammarParser.CaracterContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "boolean".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitBooleano(JackGrammarParser.BooleanoContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila la regla de la gramática type (className) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitNameClase(JackGrammarParser.NameClaseContext contexto) {
        String xml = visit(contexto.className());
        return xml;
    }

    /**
     * Método que compila la regla de la gramática subroutineDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineDec(JackGrammarParser.SubroutineDecContext contexto) {
        String XML = tabular("<subroutineDec>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // constructor | function | method
        if (contexto.type() == null) {
            XML += tabular(advance()); // void
        } else {
            XML += visit(contexto.type());
        }
        XML += visit(contexto.subroutineName());
        XML += tabular(advance()); // (
        XML += visit(contexto.parameterList());
        XML += tabular(advance()); // )
        XML += visit(contexto.subroutineBody());
        --contadorTabs;
        XML += tabular("</subroutineDec>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática parameterList y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitParameterList(JackGrammarParser.ParameterListContext contexto) {
        String XML = tabular("<parameterList>" + "\n");
        ++contadorTabs;
        if (!contexto.type().isEmpty()) {
            XML += visit(contexto.type(0));
            XML += visit(contexto.varName(0));
            int tamanoLista = contexto.type().size();
            for (int i = 1; i < tamanoLista; ++i) {
                XML += tabular(advance()); // ,
                XML += visit(contexto.type(i)) + visit(contexto.varName(i));
            }
        }
        --contadorTabs;
        XML += tabular("</parameterList>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática subroutineBody y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineBody(JackGrammarParser.SubroutineBodyContext contexto) {
        String XML = tabular("<subroutineBody>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // {
        for (JackGrammarParser.VarDecContext c: contexto.varDec()) {
            XML += visit(c);            
        }
        XML += visit(contexto.statements());
        XML += tabular(advance()); // }
        --contadorTabs;
        XML += tabular("</subroutineBody>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática varDec y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarDec(JackGrammarParser.VarDecContext contexto) {
        String XML = tabular("<varDec>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // var
        XML += visit(contexto.type()) + visit(contexto.varName(0)); 
        int tamanoLista = contexto.varName().size();
        for (int i = 1; i < tamanoLista; ++i) {
            XML += tabular(advance()); // ,
            XML += visit(contexto.varName(i));
        }
        XML += tabular(advance()); // ;
        --contadorTabs;
        XML += tabular("</varDec>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática className y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitClassName(JackGrammarParser.ClassNameContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila la regla de la gramática subroutineName y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubroutineName(JackGrammarParser.SubroutineNameContext contexto) {
        return  tabular(advance());
    }

    /**
     * Método que compila la regla de la gramática varName y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarName(JackGrammarParser.VarNameContext contexto) {
        return  tabular(advance());
    }

    /**
     * Método que compila la regla de la gramática statements y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitStatements(JackGrammarParser.StatementsContext contexto) {
        String XML = tabular("<statements>" + "\n");
        ++contadorTabs;
        for (JackGrammarParser.StatementContext c: contexto.statement()) {
            XML += visit(c);
        }
        --contadorTabs;
        XML += tabular("</statements>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática statement (opLet) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpLet(JackGrammarParser.OpLetContext contexto) {
        String xml = visit(contexto.letStatement());
        return xml;
    }

    /**
     * Método que compila la regla de la gramática statement (opIf) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpIf(JackGrammarParser.OpIfContext contexto) {
        String xml = visit(contexto.ifStatement());
        return xml;
    }

    /**
     * Método que compila la regla de la gramática statement (opWhile) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpWhile(JackGrammarParser.OpWhileContext contexto) {
        String xml = visit(contexto.whileStatement());
        return xml;
    }

    /**
     * Método que compila la regla de la gramática statement (opDo) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpDo(JackGrammarParser.OpDoContext contexto) {
        String xml = visit(contexto.doStatement());
        return xml;
    }

    /**
     * Método que compila la regla de la gramática statement (opReturn) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitOpReturn(JackGrammarParser.OpReturnContext contexto){ 
        String xml = visit(contexto.returnStatement());
        return xml;
    }
    
    /**
     * Método que compila la regla de la gramática letStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitLetStatement(JackGrammarParser.LetStatementContext contexto) {
        String XML = tabular("<letStatement>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // let
        XML += visit(contexto.varName());
        int contadorExp = 0;
        if (tokenType == 28) { // es '['?
            XML += tabular(advance()); // [
            XML += visit(contexto.expression(contadorExp));
            XML += tabular(advance()); // ]
            ++contadorExp;
        }
        XML += tabular(advance()); // =
        XML += visit(contexto.expression(contadorExp));
        XML += tabular(advance()); // ;
        --contadorTabs;
        XML += tabular("</letStatement>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática ifStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitIfStatement(JackGrammarParser.IfStatementContext contexto) {
        String XML = tabular("<ifStatement>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // if
        XML += tabular(advance()); // (
        XML += visit(contexto.expression());
        XML += tabular(advance()); // )
        XML += tabular(advance()); // {
        XML += visit(contexto.statements(0)); 
        XML += tabular(advance()); // }
        int tamanoLista = contexto.statements().size();
        if (tamanoLista > 1) {
            XML += tabular(advance()); // else
            XML += tabular(advance()); // {
            XML += visit(contexto.statements(1));
            XML += tabular(advance()); // }
        }
        --contadorTabs;
        XML += tabular("</ifStatement>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática whileStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitWhileStatement(JackGrammarParser.WhileStatementContext contexto) {
        String XML = tabular("<whileStatement>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // while
        XML += tabular(advance()); // (
        XML += visit(contexto.expression());
        XML += tabular(advance()); // )
        XML += tabular(advance()); // {
        XML += visit(contexto.statements());
        XML += tabular(advance());  // }
        --contadorTabs;
        XML += tabular("</whileStatement>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática doStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitDoStatement(JackGrammarParser.DoStatementContext contexto) {
        String XML = tabular("<doStatement>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // do
        XML += visit(contexto.subroutineCall());
        XML += tabular(advance()); // ;
        --contadorTabs;
        XML += tabular("</doStatement>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática returnStatement y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitReturnStatement(JackGrammarParser.ReturnStatementContext contexto) {
        String XML = tabular("<returnStatement>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // return
        if (contexto.expression() != null) {
            XML += visit(contexto.expression());
        }
        XML += tabular(advance()); // ;
        --contadorTabs;
        XML += tabular("</returnStatement>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática expression y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitExpression(JackGrammarParser.ExpressionContext contexto) {
        String XML = tabular("<expression>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.term(0));
        int tamanoLista = contexto.operator().size();
        for (int i = 0; i < tamanoLista; ++i) {
            XML += visit(contexto.operator(i)) + visit(contexto.term(i+1));
        }
        --contadorTabs;
        XML += tabular("</expression>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (INTEGERCONSTANT) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitIntConstant(JackGrammarParser.IntConstantContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += tabular(advance());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (STRINGCONSTANT) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitStringConstant(JackGrammarParser.StringConstantContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += tabular(advance());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (keyword_Constant) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitKeyword_Constant(JackGrammarParser.Keyword_ConstantContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.keywordConstant());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (nombreVariable) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitNombreVariable(JackGrammarParser.NombreVariableContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.varName());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (varArreglo) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitVarArreglo(JackGrammarParser.VarArregloContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.varName());
        XML += tabular(advance()); // [
        XML += visit(contexto.expression());
        XML += tabular(advance()); // ]
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (subrutinaCall) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitSubrutinaCall(JackGrammarParser.SubrutinaCallContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.subroutineCall());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (parentesis) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitParentesis(JackGrammarParser.ParentesisContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += tabular(advance()); // (
        XML += visit(contexto.expression());
        XML += tabular(advance()); // )
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática term (unaryOperator) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */    
    public String visitUnaryOperator(JackGrammarParser.UnaryOperatorContext contexto) {
        String XML = tabular("<term>" + "\n");
        ++contadorTabs;
        XML += visit(contexto.unaryOp()) + visit(contexto.term());
        --contadorTabs;
        XML += tabular("</term>" + "\n");
        return XML;
    }

    /**
     * Método que compila la regla de la gramática subroutineCall (llamadaSubrutina) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitLlamadaSubrutina(JackGrammarParser.LlamadaSubrutinaContext contexto) {
        String XML = visit(contexto.subroutineName());
        XML += tabular(advance()); // (
        XML += visit(contexto.expressionList());
        XML += tabular(advance()); // )
        return XML;
    }

    /**
     * Método que compila la regla de la gramática subroutineCall (objectCall) y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitLlamadaObjeto(JackGrammarParser.LlamadaObjetoContext contexto) {
        String XML = "";
        if (contexto.className() != null) {
            XML += visit(contexto.className());
        } else {
            XML += visit(contexto.varName());
        }
        XML += tabular(advance()); // .
        XML += visit(contexto.subroutineName());
        XML += tabular(advance()); // (
        XML += visit(contexto.expressionList());
        XML += tabular(advance()); // )
        return XML;
    }

    /**
     * Método que compila la regla de la gramática expressionList y retorna el string
     * que contiene las palabras claves y los símbolos.
     * @param contexto
     * @return XML String que contiene las palabras claves y símbolos generados.
     */
    public String visitExpressionList(JackGrammarParser.ExpressionListContext contexto) {
        String XML = tabular("<expressionList>" + "\n");
        ++contadorTabs;
        if (!contexto.expression().isEmpty()) {
            XML += visit(contexto.expression(0));
            int tamanoLista = contexto.expression().size();
            for (int i = 1; i < tamanoLista; ++i) {
                XML += tabular(advance()); // ,
                XML += visit(contexto.expression(i));
            }
        }
        --contadorTabs;
        XML += tabular("</expressionList>" + "\n");
        return XML;
    }

    /**
     * Método que compila el literal de la gramática "+".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitSuma(JackGrammarParser.SumaContext contexto) {
        return tabular(advance());
    }
 
    /**
     * Método que compila el literal de la gramática "-".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitResta(JackGrammarParser.RestaContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "*".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitMult(JackGrammarParser.MultContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "/".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitDivision(JackGrammarParser.DivisionContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "&".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitAnd(JackGrammarParser.AndContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "|".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitOr(JackGrammarParser.OrContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "<".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitMenor(JackGrammarParser.MenorContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática ">".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitMayor(JackGrammarParser.MayorContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "=".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitIgual(JackGrammarParser.IgualContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "-".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitNeg(JackGrammarParser.NegContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "~".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitNot(JackGrammarParser.NotContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "true".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitTrue(JackGrammarParser.TrueContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "false".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitFalse(JackGrammarParser.FalseContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "null".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitNull(JackGrammarParser.NullContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que compila el literal de la gramática "this".
     * @param contexto
     * @return String que contiene el literal generado.
     */
    public String visitThis(JackGrammarParser.ThisContext contexto) {
        return tabular(advance());
    }

    /**
     * Método que permite obtener la extensión del archivo ingresado en los argumentos.
     * @param nombreArchivo
     * @return
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