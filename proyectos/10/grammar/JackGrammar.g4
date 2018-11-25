grammar JackGrammar;

classJack       : 'class' className '{'  (classVarDec)* (subroutineDec)* '}'
                ;

classVarDec     : ('static' | 'field') type varName (',' varName)* ';'
                ;

type            : 'int'         # entero
                | 'char'        # caracter
                | 'boolean'     # booleano
                | className     # nameClase
                ;

subroutineDec   : ('constructor' | 'function' | 'method') ('void' | type) subroutineName'(' parameterList ')' subroutineBody
                ;

parameterList   : ((type varName)(',' type varName)*)?
                ;

subroutineBody  : '{' (varDec)* statements '}'
                ;

varDec		: 'var' type varName(','varName)* ';'
                ;


className       : IDENTIFIER
                ;

subroutineName  : IDENTIFIER
                ;

varName         : IDENTIFIER
                ;

statements      : (statement)*
                ;
    
statement       : letStatement      # opLet
                | ifStatement       # opIf
                | whileStatement    # opWhile
                | doStatement       # opDo
                | returnStatement   # opReturn
                ;

letStatement    : 'let' varName ('[' expression ']')? '=' expression ';'
                ;

ifStatement     : 'if' '(' expression ')' '{' statements '}' ('else' '{' statements '}')?
                ;

whileStatement  : 'while' '(' expression ')' '{' statements'}'
                ;

doStatement     : 'do' subroutineCall ';'
                ;

returnStatement : 'return' (expression)? ';'
                ;

expression      : term (operator term)*
                ;

term            : INTEGER_CONSTANT       # intConstant
                | STRING_CONSTANT        # stringConstant
                | keywordConstant       # keyword_Constant
                | varName               # nombreVariable
                | varName'[' expression ']'     # varArreglo
                | subroutineCall        # subrutinaCall
                | '(' expression ')'    # parentesis
                | unaryOp term    # unaryOperator
                ;

subroutineCall  : subroutineName'(' expressionList ')'     # llamadaSubrutina
                | (className | varName ) '.' subroutineName '(' expressionList ')' # llamadaObjeto
                ;

expressionList  : (expression (','expression)* )?
                ;

operator        : '+'       # suma
                | '-'       # resta
                | '*'       # mult
                | '/'       # division
                | '&'       # and
                | '|'       # or
                | '<'       # menor
                | '>'       # mayor
                | '='       # igual
                ;

unaryOp         : '-'       # neg
                | '~'       # not
                ;

keywordConstant : 'true'     # true
                | 'false'    # false
                | 'null'     # null
                | 'this'     # this
                ;


INTEGER_CONSTANT
        : (('1'..'9')('0'..'9')* | '0')
        ;

STRING_CONSTANT
        : '"' .*?  '"'
        ;

KEYWORD1 : 'class'
    ;
KEYWORD2 : 'constructor'
    ;
KEYWORD3 : 'function'
    ;
KEYWORD4 : 'method'
    ;
KEYWORD5 : 'field'
    ;
KEYWORD6 : 'static'
    ;
KEYWORD7 : 'var'
    ;
KEYWORD8 : 'int'
    ;
KEYWORD9 : 'char'
        ;
KEYWORD10    : 'boolean'
        ;
KEYWORD11    : 'void'
        ;
KEYWORD12    : 'true'
        ;
KEYWORD13    : 'false'
        ;
KEYWORD14    : 'null'
        ;
KEYWORD15    : 'this'
        ;
KEYWORD16    : 'let'
        ;
KEYWORD17    : 'do'
        ;
KEYWORD18    : 'if'
        ;
KEYWORD19    : 'else'
        ;
KEYWORD20    : 'while'
        ;
KEYWORD21    : 'return'
        ;


SYMBOL1    : '{'
        ;
SYMBOL2    : '}'
        ;
SYMBOL3    : '('
        ;
SYMBOL4    : ')'
        ;
SYMBOL5    : '['
        ;
SYMBOL6    : ']'
        ;
SYMBOL7    : '.'
        ;
SYMBOL8    : ','
        ;
SYMBOL9    : ';'
        ;
SYMBOL10   : '+'
        ;
SYMBOL11   : '-'
        ;
SYMBOL12   : '*'
        ;
SYMBOL13   : '/'
        ;
SYMBOL14   : '&'
        ;
SYMBOL15   : '|'
        ;
SYMBOL16   : '<'
        ;
SYMBOL17   : '>'
        ;
SYMBOL18   : '='
        ;
SYMBOL19   : '~'
        ;

IDENTIFIER
        : ('a'..'z'|'A'..'Z')('_'|'a'..'z'|'A'..'Z'|'0'..'9')*
        ;


WS  : (' '
    | '\t'
    | '\r'
    | '\n'
    | '\f'
    ) -> skip
    ;

LINE_COMMENT
    : '//' .*? '\r'? '\n' -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;