package ast;

public class Alias extends Statement {
    private final Name alias, forName;

    public Alias(Name alias, Name forName) {
        this.alias = alias;
        this.forName = forName;
    }

    public Name getAlias() {
        return alias;
    }

    public Name getForName() {
        return forName;
    }

    @Override
    public <C, T> T accept(C context, TinyVarsVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
