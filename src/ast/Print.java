package ast;

public class Print extends Statement {
    private final Exp printed;

    public Print(Exp printed) {
        this.printed = printed;
    }

    public Exp getPrinted() {
        return printed;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }
}
