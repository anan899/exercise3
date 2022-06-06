package ast;

public class Dec extends Statement {
    private final Name name;

    public Dec(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }

}
