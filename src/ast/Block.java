package ast;

import java.util.List;

public class Block extends Statement {
    private final List<Statement> statements;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public <C, T> T accept(C context, TinyVarsVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
