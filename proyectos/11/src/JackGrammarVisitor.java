// Generated from JackGrammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JackGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JackGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#classJack}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassJack(JackGrammarParser.ClassJackContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#classVarDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassVarDec(JackGrammarParser.ClassVarDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code entero}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntero(JackGrammarParser.EnteroContext ctx);
	/**
	 * Visit a parse tree produced by the {@code caracter}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaracter(JackGrammarParser.CaracterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleano}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleano(JackGrammarParser.BooleanoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nameClase}
	 * labeled alternative in {@link JackGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameClase(JackGrammarParser.NameClaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#subroutineDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubroutineDec(JackGrammarParser.SubroutineDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(JackGrammarParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#subroutineBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubroutineBody(JackGrammarParser.SubroutineBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDec(JackGrammarParser.VarDecContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#className}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassName(JackGrammarParser.ClassNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#subroutineName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubroutineName(JackGrammarParser.SubroutineNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#varName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarName(JackGrammarParser.VarNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(JackGrammarParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opLet}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLet(JackGrammarParser.OpLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opIf}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpIf(JackGrammarParser.OpIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opWhile}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpWhile(JackGrammarParser.OpWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opDo}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpDo(JackGrammarParser.OpDoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code opReturn}
	 * labeled alternative in {@link JackGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpReturn(JackGrammarParser.OpReturnContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#letStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetStatement(JackGrammarParser.LetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(JackGrammarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(JackGrammarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(JackGrammarParser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(JackGrammarParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(JackGrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntConstant(JackGrammarParser.IntConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringConstant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringConstant(JackGrammarParser.StringConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code keyword_Constant}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword_Constant(JackGrammarParser.Keyword_ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nombreVariable}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNombreVariable(JackGrammarParser.NombreVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varArreglo}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarArreglo(JackGrammarParser.VarArregloContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subrutinaCall}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubrutinaCall(JackGrammarParser.SubrutinaCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentesis}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentesis(JackGrammarParser.ParentesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryOperator}
	 * labeled alternative in {@link JackGrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperator(JackGrammarParser.UnaryOperatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code llamadaSubrutina}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamadaSubrutina(JackGrammarParser.LlamadaSubrutinaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code llamadaObjeto}
	 * labeled alternative in {@link JackGrammarParser#subroutineCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamadaObjeto(JackGrammarParser.LlamadaObjetoContext ctx);
	/**
	 * Visit a parse tree produced by {@link JackGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(JackGrammarParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suma}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuma(JackGrammarParser.SumaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code resta}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResta(JackGrammarParser.RestaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mult}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(JackGrammarParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by the {@code division}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivision(JackGrammarParser.DivisionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(JackGrammarParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(JackGrammarParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code menor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMenor(JackGrammarParser.MenorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mayor}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMayor(JackGrammarParser.MayorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code igual}
	 * labeled alternative in {@link JackGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIgual(JackGrammarParser.IgualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code neg}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNeg(JackGrammarParser.NegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not}
	 * labeled alternative in {@link JackGrammarParser#unaryOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(JackGrammarParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(JackGrammarParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(JackGrammarParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code null}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull(JackGrammarParser.NullContext ctx);
	/**
	 * Visit a parse tree produced by the {@code this}
	 * labeled alternative in {@link JackGrammarParser#keywordConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThis(JackGrammarParser.ThisContext ctx);
}