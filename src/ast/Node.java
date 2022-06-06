package ast;

public abstract class Node {
    abstract public <C,T> T accept(C context, TinyVarsVisitor<C,T> v); // so that we remember to define this in all subclasses
}
