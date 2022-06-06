package ast;

import java.util.List;

public class Program extends Node{
    private final List<Statement> statements;

    public List<Statement> getStatements() {
        return statements;
    }

    public Program(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
