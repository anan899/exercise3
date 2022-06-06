package parser;

import ast.*;
import ast.Number;
import java.util.ArrayList;
import java.util.List;

 public class ParseTreeToAST extends TinyVarsParserBaseVisitor<Node> { // this gives default implementations (but you need to be careful not to forget to override the ones you want!)
    @Override
    public Program visitProgram(TinyVarsParser.ProgramContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for(TinyVarsParser.StatementContext s : ctx.statements().statement()) {
          statements.add(this.visitStatement(s));
        }
        return new Program(statements);
    }

    @Override
    public Statement visitStatement(TinyVarsParser.StatementContext ctx) {

        if(ctx.declare() != null) {
            return visitDeclare(ctx.declare());
        } else if (ctx.assign() != null) {
            return visitAssign(ctx.assign());
        } else if (ctx.print() != null) {
            return visitPrint(ctx.print());
        } else if (ctx.undef() != null) {
            return visitUndef(ctx.undef());
        } else if (ctx.alias() != null) {
            return visitAlias(ctx.alias());
        } else if (ctx.conditional() != null) {
            return visitConditional(ctx.conditional());
        } else{
            throw new RuntimeException("Statement parse tree with invalid context information");
        }
    }

    @Override
    public Dec visitDeclare(TinyVarsParser.DeclareContext ctx) {
        return new Dec(visitVarname(ctx.varname()));
    }

    @Override
    public Set visitAssign(TinyVarsParser.AssignContext ctx) {
        return new Set(visitVarname(ctx.varname()), visitExp(ctx.exp()));
    }

     // newly added for this version of tinyVars
     @Override
     public Undef visitUndef(TinyVarsParser.UndefContext ctx) {
         return new Undef(visitVarname(ctx.varname()));
     }

     // newly added for this version of tinyVars
     @Override
     public Alias visitAlias(TinyVarsParser.AliasContext ctx) {
         return new Alias(visitVarname(ctx.varname(0)), visitVarname(ctx.varname(1)));
     }

     @Override
    public Print visitPrint(TinyVarsParser.PrintContext ctx) {
        return new Print(visitExp(ctx.exp()));
    }

     @Override
     public Conditional visitConditional(TinyVarsParser.ConditionalContext ctx) {
         return new Conditional(visitExp(ctx.exp()), visitBlock(ctx.block(0)), visitBlock(ctx.block(1)));
     }

     @Override
     public Block visitBlock(TinyVarsParser.BlockContext ctx) {
         List<Statement> statements = new ArrayList<>();
         for(TinyVarsParser.StatementContext s : ctx.statements().statement()) {
             statements.add(this.visitStatement(s));
         }
         return new Block(statements);
     }

     @Override
    public Exp visitExp(TinyVarsParser.ExpContext ctx) {
        if(ctx.varname() != null) {
            return visitVarname(ctx.varname());
        } else if (ctx.CONST() != null) {
            return new Number(Integer.parseInt(ctx.CONST().getText()));
        } else if (ctx.COMMANDLINEARG() != null) {
                // Remove leading $ to parse index
                return new CommandLineArg(Integer.parseInt(ctx.COMMANDLINEARG().getText().substring(1)));
        } else {
            throw new RuntimeException("Expression parse tree with invalid context information");
        }
    }

    @Override
    public Name visitVarname(TinyVarsParser.VarnameContext ctx) {
        return new Name(ctx.NAME().getText());
    }
}
