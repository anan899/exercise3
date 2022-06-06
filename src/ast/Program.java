package ast;

import java.util.ArrayList;
import java.util.List;

public class Program extends Node{
    private final List<Statement> statements;

    private final List<ProcDeclaration> procs;

    public List<Statement> getStatements() {
        return statements;
    }

    public Program(List<ProcDeclaration> procs, List<Statement> statements) {
        this.procs = procs; this.statements = statements;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }

    public List<ProcDeclaration> getProcs() {
        return procs;
    }
}
