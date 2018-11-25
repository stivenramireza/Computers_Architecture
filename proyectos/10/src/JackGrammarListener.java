// Generated from JackGrammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JackGrammarParser}.
 */
public interface JackGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#classJack}.
	 * @param ctx the parse tree
	 */
	void enterClassJack(JackGrammarParser.ClassJackContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#classJack}.
	 * @param ctx the parse tree
	 */
	void exitClassJack(JackGrammarParser.ClassJackContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#classVarDec}.
	 * @param ctx the parse tree
	 */
	void enterClassVarDec(JackGrammarParser.ClassVarDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#classVarDec}.
	 * @param ctx the parse tree
	 */
	void exitClassVarDec(JackGrammarParser.ClassVarDecContext ctx);
	/**
	 * Enter a parse tree produced by the {@code entero}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterEntero(JackGrammarParser.EnteroContext ctx);
	/**
	 * Exit a parse tree produced by the {@code entero}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitEntero(JackGrammarParser.EnteroContext ctx);
	/**
	 * Enter a parse tree produced by the {@code caracter}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterCaracter(JackGrammarParser.CaracterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code caracter}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitCaracter(JackGrammarParser.CaracterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleano}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterBooleano(JackGrammarParser.BooleanoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleano}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitBooleano(JackGrammarParser.BooleanoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nameClase}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterNameClase(JackGrammarParser.NameClaseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nameClase}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitNameClase(JackGrammarParser.NameClaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#subroutineDec}.
	 * @param ctx the parse tree
	 */
	void enterSubroutineDec(JackGrammarParser.SubroutineDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#subroutineDec}.
	 * @param ctx the parse tree
	 */
	void exitSubroutineDec(JackGrammarParser.SubroutineDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(JackGrammarParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(JackGrammarParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#subroutineBody}.
	 * @param ctx the parse tree
	 */
	void enterSubroutineBody(JackGrammarParser.SubroutineBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#subroutineBody}.
	 * @param ctx the parse tree
	 */
	void exitSubroutineBody(JackGrammarParser.SubroutineBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#varDec}.
	 * @param ctx the parse tree
	 */
	void enterVarDec(JackGrammarParser.VarDecContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#varDec}.
	 * @param ctx the parse tree
	 */
	void exitVarDec(JackGrammarParser.VarDecContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(JackGrammarParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(JackGrammarParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#subroutineName}.
	 * @param ctx the parse tree
	 */
	void enterSubroutineName(JackGrammarParser.SubroutineNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#subroutineName}.
	 * @param ctx the parse tree
	 */
	void exitSubroutineName(JackGrammarParser.SubroutineNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#varName}.
	 * @param ctx the parse tree
	 */
	void enterVarName(JackGrammarParser.VarNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#varName}.
	 * @param ctx the parse tree
	 */
	void exitVarName(JackGrammarParser.VarNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(JackGrammarParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(JackGrammarParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opLet}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterOpLet(JackGrammarParser.OpLetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opLet}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitOpLet(JackGrammarParser.OpLetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opIf}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterOpIf(JackGrammarParser.OpIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opIf}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitOpIf(JackGrammarParser.OpIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opWhile}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterOpWhile(JackGrammarParser.OpWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opWhile}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitOpWhile(JackGrammarParser.OpWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opDo}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterOpDo(JackGrammarParser.OpDoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opDo}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitOpDo(JackGrammarParser.OpDoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code opReturn}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterOpReturn(JackGrammarParser.OpReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code opReturn}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitOpReturn(JackGrammarParser.OpReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#letStatement}.
	 * @param ctx the parse tree
	 */
	void enterLetStatement(JackGrammarParser.LetStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#letStatement}.
	 * @param ctx the parse tree
	 */
	void exitLetStatement(JackGrammarParser.LetStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(JackGrammarParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(JackGrammarParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(JackGrammarParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(JackGrammarParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(JackGrammarParser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#doStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(JackGrammarParser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(JackGrammarParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(JackGrammarParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(JackGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(JackGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterIntConstant(JackGrammarParser.IntConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitIntConstant(JackGrammarParser.IntConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterStringConstant(JackGrammarParser.StringConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitStringConstant(JackGrammarParser.StringConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code keyword_Constant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterKeyword_Constant(JackGrammarParser.Keyword_ConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code keyword_Constant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitKeyword_Constant(JackGrammarParser.Keyword_ConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nombreVariable}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNombreVariable(JackGrammarParser.NombreVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nombreVariable}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNombreVariable(JackGrammarParser.NombreVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varArreglo}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterVarArreglo(JackGrammarParser.VarArregloContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varArreglo}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitVarArreglo(JackGrammarParser.VarArregloContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subrutinaCall}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterSubrutinaCall(JackGrammarParser.SubrutinaCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subrutinaCall}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitSubrutinaCall(JackGrammarParser.SubrutinaCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterParentesis(JackGrammarParser.ParentesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitParentesis(JackGrammarParser.ParentesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryOperator}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(JackGrammarParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryOperator}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(JackGrammarParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code llamadaSubrutina}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 */
	void enterLlamadaSubrutina(JackGrammarParser.LlamadaSubrutinaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code llamadaSubrutina}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 */
	void exitLlamadaSubrutina(JackGrammarParser.LlamadaSubrutinaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code llamadaObjeto}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 */
	void enterLlamadaObjeto(JackGrammarParser.LlamadaObjetoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code llamadaObjeto}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 */
	void exitLlamadaObjeto(JackGrammarParser.LlamadaObjetoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JackGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(JackGrammarParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JackGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(JackGrammarParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suma}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterSuma(JackGrammarParser.SumaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suma}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitSuma(JackGrammarParser.SumaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code resta}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterResta(JackGrammarParser.RestaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code resta}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitResta(JackGrammarParser.RestaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterMult(JackGrammarParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitMult(JackGrammarParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by the {@code division}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterDivision(JackGrammarParser.DivisionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code division}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitDivision(JackGrammarParser.DivisionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterAnd(JackGrammarParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitAnd(JackGrammarParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOr(JackGrammarParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOr(JackGrammarParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code menor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterMenor(JackGrammarParser.MenorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code menor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitMenor(JackGrammarParser.MenorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mayor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterMayor(JackGrammarParser.MayorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mayor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitMayor(JackGrammarParser.MayorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code igual}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterIgual(JackGrammarParser.IgualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code igual}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitIgual(JackGrammarParser.IgualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code neg}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 */
	void enterNeg(JackGrammarParser.NegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 */
	void exitNeg(JackGrammarParser.NegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 */
	void enterNot(JackGrammarParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 */
	void exitNot(JackGrammarParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void enterTrue(JackGrammarParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void exitTrue(JackGrammarParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void enterFalse(JackGrammarParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void exitFalse(JackGrammarParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code null}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void enterNull(JackGrammarParser.NullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code null}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void exitNull(JackGrammarParser.NullContext ctx);
	/**
	 * Enter a parse tree produced by the {@code this}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void enterThis(JackGrammarParser.ThisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code this}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 */
	void exitThis(JackGrammarParser.ThisContext ctx);
}