import org.antlr.v4.runtime.*;
/**
 * Esta clase permite recorrer el lexema generado por la gramática y a partir de él,
 * identificar los tipos de tokens que se encuentran en el archivo .jack para luego
 * pasarlo a lenguaje de máquina virtual.
 * 
 * @author Stiven Ramírez Arango
 * @version 1.0
 */
public class TraductorTokens {

    /**
     * Este método permite avanzar en el recorrido del archivo .jack identificando el
     * final del fichero y obteniendo cada uno de los tokens.
     * 
     * @param lexer
     * @return XML Archivo generado en XML
     */
    public static String advance(JackGrammarLexer lexer) {
        Token token = lexer.nextToken();
        Token eof = lexer.emitEOF();
        String XML = "<tokens>" + "\n";
        int tokenType = token.getType();
        while (tokenType != eof.getType()) {
            XML = XML + tipoToken(tokenType, token);
            token = lexer.nextToken();
            tokenType = token.getType();
        }
        XML = XML + "</tokens>";
        return XML;
    }

    /**
     * Este método genera las cadenas que permiten escribir el archivo xml
     * y la identificación de sus tokens para el lenguaje de máquina virtual.
     * 
     * @param tokenType
     * @param token
     * @return xmlToken String que contiene los tokens generados.
     */
    public static String tipoToken(int tokenType, Token token) {
        String XMLToken = "";
        switch (tokenType) {
        case JackGrammarLexer.INTEGER_CONSTANT:
            XMLToken = "<integerConstant>" + token.getText() + "</integerConstant>";
            break;
        case JackGrammarLexer.STRING_CONSTANT:
            String temp = token.getText();
            temp = temp.substring(1, temp.length()-1);
            XMLToken = "<stringConstant>" + temp + "</stringConstant>";
            break;
        case JackGrammarLexer.KEYWORD1: // class
        case JackGrammarLexer.KEYWORD2: // constructor
        case JackGrammarLexer.KEYWORD3: // function
        case JackGrammarLexer.KEYWORD4: // method
        case JackGrammarLexer.KEYWORD5: // field
        case JackGrammarLexer.KEYWORD6: // static
        case JackGrammarLexer.KEYWORD7: // var
        case JackGrammarLexer.KEYWORD8: // int
        case JackGrammarLexer.KEYWORD9: // char
        case JackGrammarLexer.KEYWORD10: // boolean
        case JackGrammarLexer.KEYWORD11: // void
        case JackGrammarLexer.KEYWORD12: // true
        case JackGrammarLexer.KEYWORD13: // false
        case JackGrammarLexer.KEYWORD14: // null
        case JackGrammarLexer.KEYWORD15: // this
        case JackGrammarLexer.KEYWORD16: // let
        case JackGrammarLexer.KEYWORD17: // do
        case JackGrammarLexer.KEYWORD18: // if
        case JackGrammarLexer.KEYWORD19: // else
        case JackGrammarLexer.KEYWORD20: // while
        case JackGrammarLexer.KEYWORD21: // return
            XMLToken = "<keyword>" + token.getText() + "</keyword>";
            break;
        case JackGrammarLexer.SYMBOL1: // {
        case JackGrammarLexer.SYMBOL2: // }
        case JackGrammarLexer.SYMBOL3: // (
        case JackGrammarLexer.SYMBOL4: // )
        case JackGrammarLexer.SYMBOL5: // [
        case JackGrammarLexer.SYMBOL6: // ]
        case JackGrammarLexer.SYMBOL7: // .
        case JackGrammarLexer.SYMBOL8: // ,
        case JackGrammarLexer.SYMBOL9: // ;
        case JackGrammarLexer.SYMBOL10: // +
        case JackGrammarLexer.SYMBOL11: // -
        case JackGrammarLexer.SYMBOL12: // *
        case JackGrammarLexer.SYMBOL13: // /
        case JackGrammarLexer.SYMBOL15: // |
        case JackGrammarLexer.SYMBOL18: // =
        case JackGrammarLexer.SYMBOL19: // ~
            XMLToken = "<symbol>" + token.getText() + "</symbol>";
            break;
        case JackGrammarLexer.SYMBOL14: // &
            XMLToken = "<symbol>" + "&amp;" + "</symbol>";
            break;
        case JackGrammarLexer.SYMBOL16: // <
            XMLToken = "<symbol>" + "&lt;" + "</symbol>";
            break;
        case JackGrammarLexer.SYMBOL17: // >
            XMLToken = "<symbol>" + "&gt;" + "</symbol>";
            break;
        case JackGrammarLexer.IDENTIFIER:
            XMLToken = "<identifier>" + token.getText() + "</identifier>";
            break;
        case JackGrammarLexer.WS: // Espacios en blanco
            break;
        case JackGrammarLexer.LINE_COMMENT: // Comentarios con lineas
            break;
        case JackGrammarLexer.BLOCK_COMMENT: // Comentarios con bloques
            break;
        default:
            System.err.println("Lexer ERROR: token: "+ token.getText() + " en la línea: " + token.getLine() + " y columna: " + token.getCharPositionInLine());
            System.exit(1);
        }
        return XMLToken + "\n";
    }
}