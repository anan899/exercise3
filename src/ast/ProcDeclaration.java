package ast;

import java.util.List;

public class ProcDeclaration extends Node {
    private final Name name;
    private final List<Name> params;
    private final List<Statement> body;

    public ProcDeclaration(Name name, List<Name> params, List<Statement> body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }

    public Name getName() {
        return name;
    }

    public List<Name> getParams() {
        return params;
    }

    public List<Statement> getBody() {
        return body;
    }
}
