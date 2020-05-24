grammar Hello;

PROGRAM:    'program' ;
VAR:        'var';
TYPE:       'int' | 'float'| 'bool' | 'string';
REPEAT:     'repeat';
UNTIL:      'until';
IF:         'if';
THEN:       'then';
FI:         'fi';
IN:         'in';
OUT:        'out';
BOOLEAN:    'true' | 'false';

ASSIGN_OP:  '=';
SIGN:       '+' | '-';
MULT_OP:    '*' | '/' | '%' | '^';

COMPARE_OP: '<=' | '<' | '>' | '>=' | '!=' | '==';
OR:         '||';
AND:        '&&';
NOT:        '!';

L_PAR:      '(';
R_PAR:      ')';
OPEN:       '{';
CLOSE:      '}';
HASH:       '#';

COMA:       ',';
COLON:      ':';
SEMICOLON:  ';';
DOT:        '.';

IDENTIFIER: (LETTER | HASH) (LETTER | NUMBER | HASH)*;
LETTER:     [a-zA-Z];
NUMBER:     [0-9]+;
STRING:     '"' (~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\'))* '"';
WS:         [ \t\r\n]+ -> skip ;

r_id:
    PROGRAM IDENTIFIER
    VAR  varList SEMICOLON
    OPEN statementList SEMICOLON CLOSE
;

varList:
    declaration (SEMICOLON declaration)*
;

declaration:
    identList COLON TYPE
;

identList:
    IDENTIFIER (COMA IDENTIFIER)*
;

statementList:
    statement (SEMICOLON statement)*
;

statement:
    assign | input | output | r_if| r_repeat
;

input:
    IN L_PAR identList R_PAR
;

output:
    OUT L_PAR (arithmExpr | identList) R_PAR
;

r_repeat:
    REPEAT OPEN statementList SEMICOLON CLOSE UNTIL L_PAR condition R_PAR
;

condition:
    (NOT conditionTerm | conditionTerm OR condition | conditionTerm)
;

conditionTerm:
    (conditionFactor AND conditionTerm| conditionFactor )
;

conditionFactor:
    (boolExpr | L_PAR condition R_PAR) //condition )
;

r_if:
    IF L_PAR condition R_PAR THEN
    (statement SEMICOLON | OPEN statementList SEMICOLON CLOSE)
    FI
;

assign:
    IDENTIFIER ASSIGN_OP (boolExpr | arithmExpr | STRING)
;

boolExpr:
    arithmExpr | arithmExpr COMPARE_OP arithmExpr
;

arithmExpr:
    term | term (SIGN term)
;

term:
    factor | factor (MULT_OP term)
;

factor:
    SIGN? r_const
        | SIGN? L_PAR arithmExpr R_PAR
        | SIGN? IDENTIFIER
;

r_const:
    r_int | SIGN? unsignedDouble | BOOLEAN
;

r_int:
    SIGN? NUMBER
;

unsignedDouble:
    DOT NUMBER | NUMBER DOT | NUMBER DOT NUMBER
;

