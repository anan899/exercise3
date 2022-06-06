parser grammar TinyVarsParser;
options { tokenVocab=TinyVarsLexer; }

// Grammar (as in slide deck 5)
program   : proc_declare* (statement (STMT_NEWLINE | NEWLINE))*; // updated for procedures
proc_declare: PROC procname params OPEN_BRACE_EXP NEWLINE
                statements
              CLOSE_BRACE STMT_NEWLINE; // new for procedures (entire rule)
params: OPEN_PAREN (varname (COMMA varname)*)? CLOSE_PAREN; // new for procedures
args: OPEN_PAREN (exp (COMMA exp)*)? CLOSE_PAREN; // new for procedures

statement : declare | assign | print | call; // updated for procedures
declare   : NEW varname SEMICOLON ;
assign    : SET varname COMMA exp SEMICOLON ;
print     : PRINT exp SEMICOLON ;
call      : CALL procname args SEMICOLON; // new for procedures
statements: (statement NEWLINE)*;
exp       : varname | CONST;
varname       : NAME ;
procname      : NAME ;


