package ast;

import ui.Main;

public class Dec extends Statement {
    private Name name;

    public Dec(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public <C,T> T accept(C context, TinyVarsVisitor<C,T> v) {
        return v.visit(context, this);
    }

}
