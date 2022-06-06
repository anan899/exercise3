lexer grammar TinyVarsLexer;

// we use the default mode only to recognise keywords that start statements
LEADING_SPACE : [ \t]+ -> channel(HIDDEN); // before a keyword
NEW: 'new ' -> mode(EXP_MODE);     // including a single space in the token forces that one is always used
SET: 'set ' -> mode(EXP_MODE);
PRINT: 'print ' -> mode(EXP_MODE);
UNDEF: 'undef ' -> mode(EXP_MODE);
ALIAS: 'alias ' -> mode(EXP_MODE);
IF: 'if' -> mode(EXP_MODE); // newly added for this version of tinyVars
CLOSE_BRACE : '}'; // newly added for this version of tinyVars
ELSE : 'else'-> mode(EXP_MODE); // newly added for this version of tinyVars
STMT_SPACE : [ \t]+ -> channel(HIDDEN);
STMT_NEWLINE: [\r\n]+ -> channel(HIDDEN); // newlines while in statement mode

mode EXP_MODE;

NAME  : [A-Za-z][A-Za-z0-9]*;
CONST : '-'?[0-9]+;
COMMANDLINEARG : '$'[0-9]+;
COMMA : ',';
OPEN_PAREN : '(';
CLOSE_PAREN : ')';
OPEN_BRACE : '{' -> mode(DEFAULT_MODE);
SEMICOLON : ';' -> mode(DEFAULT_MODE); // changed this to end EXP_MODE on SEMICOLON instead of NEWLINE
// spaces and tabs can be ignored (apart from those immediately following a keyword above):
SPACE : [ \t]+ -> channel(HIDDEN);
NEWLINE : [\r\n]+ -> channel(HIDDEN);