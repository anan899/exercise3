package ast;

public class CommandLineArg extends Exp {
    private final int index;

    public CommandLineArg(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public <C, T> T accept(C context, TinyVarsVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
