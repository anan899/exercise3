package ast;

public interface TinyVarsVisitor<C,T> {
    // Recall: one visit method per concrete AST node subclass
    T visit(C context, Program p);
    T visit(C context, Dec d);
    T visit(C context, Set s);
    T visit(C context, Print p);
    T visit(C context, Name n);
    T visit(C context, Number n);
    T visit(C context, Undef n);
    T visit(C context, Alias n);
    T visit(C context, Conditional n); // added for this version of tinyVars
    T visit(C context, Block n); // added for this version of tinyVars
    T visit(C context, CommandLineArg n); // added for this version of tinyVars

}
