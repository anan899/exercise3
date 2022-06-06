package ast;

public class Conditional extends Statement {
    private Exp condition;
    private Block ifBlock, elseBlock;

    public Conditional(Exp condition, Block ifBlock, Block elseBlock) {
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    public Exp getCondition() {
        return condition;
    }

    public Block getIfBlock() {
        return ifBlock;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    @Override
    public <C, T> T accept(C context, TinyVarsVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
