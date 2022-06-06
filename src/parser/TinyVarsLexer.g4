lexer grammar TinyVarsLexer;

// we use the default mode only to recognise keywords that start statements
LEADING_SPACE : [ \t]+ -> channel(HIDDEN); // before a keyword
NEW: 'new ' -> mode(EXP_MODE);     // including a single space in the token forces that one is always used
SET: 'set ' -> mode(EXP_MODE);
PRINT: 'print ' -> mode(EXP_MODE);
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
STMT_NEWLINE: [\r\n]+; // newlines while in statement mode
PROC: 'proc ' -> mode(EXP_MODE); // new for procedures; ideally we'd use a special "PROC_MODE" here, but re-using EXP_MODE doesn't hurt us here
CALL: 'call ' -> mode(EXP_MODE); // new for procedures

mode EXP_MODE;

NAME  : [A-Za-z][A-Za-z0-9]*;
CONST : '-'?[0-9]+;
COMMA : ',';
SEMICOLON : ';';
// spaces and tabs can be ignored (apart from those immediately following a keyword above):
SPACE : [ \t]+ -> channel(HIDDEN);
// newline(s) indicate end of a statement and start of the next
NEWLINE : [\r\n]+ -> mode(DEFAULT_MODE); // some number of these.. longest match means we won't go back to EXP_MODE "too early"
OPEN_BRACE_EXP: '{'; // open brace just after elements of an exp
OPEN_PAREN: '('; // to start an arguments list
CLOSE_PAREN: ')'; // to end an arguments list