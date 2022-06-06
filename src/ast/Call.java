package ast;

import java.util.List;

public class Call extends Statement {
    private final Name name;
    private final List<Exp> args;

    public Call(Name name, List<Exp> args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }

    public Name getName() {
        return name;
    }

    public List<Exp> getArgs() {
        return args;
    }
}
