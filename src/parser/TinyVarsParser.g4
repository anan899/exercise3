parser grammar TinyVarsParser;
options { tokenVocab=TinyVarsLexer; }

program     : statements;
statements  : statement*; // updated for this version of tinyVars
statement   : declare | assign | print | undef | alias | conditional; // updated for this version of tinyVars
declare     : NEW varname SEMICOLON;
assign      : SET varname COMMA exp SEMICOLON;
print       : PRINT exp SEMICOLON;
undef       : UNDEF varname SEMICOLON;
alias       : ALIAS varname COMMA varname SEMICOLON;
conditional : IF OPEN_PAREN exp CLOSE_PAREN block ELSE block;
block       : OPEN_BRACE statements CLOSE_BRACE;
exp         : varname | CONST | COMMANDLINEARG;
varname     : NAME;

