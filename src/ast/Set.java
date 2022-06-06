package ast;

public class Set extends Statement {
    private final Name name;
    private final Exp exp;

    public Set(Name name, Exp exp) {
        this.name = name;
        this.exp = exp;
    }

    public Name getName() {
        return name;
    }

    public Exp getExp() {
        return exp;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
