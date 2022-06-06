package parser;

import ast.*;
import ast.Number;
import java.util.ArrayList;
import java.util.List;

 public class ParseTreeToAST extends TinyVarsParserBaseVisitor<Node> { // this gives default implementations (but you need to be careful not to forget to override the ones you want!)

    @Override
    public Call visitCall(TinyVarsParser.CallContext ctx) {
        Name name = visitProcname(ctx.procname());
        List<Exp> args = new ArrayList<>();
        for(TinyVarsParser.ExpContext x : ctx.args().exp()) {
            args.add(visitExp(x));
        }
        return new Call(name,args);
    }

     @Override
     public Name visitProcname(TinyVarsParser.ProcnameContext ctx) {
         return new Name(ctx.NAME().getText());
     }

    @Override
    public ProcDeclaration visitProc_declare(TinyVarsParser.Proc_declareContext ctx) {
      Name name = visitProcname(ctx.procname());
      List<Name> params = new ArrayList<>();
      for(TinyVarsParser.VarnameContext x : ctx.params().varname()) {
          params.add(visitVarname(x));
      }
      List<Statement> statements = new ArrayList<>();
      for(TinyVarsParser.StatementContext s : ctx.statements().statement()) {
          statements.add(visitStatement(s));
      }
      return new ProcDeclaration(name,params,statements);
    }

    @Override
    public Program visitProgram(TinyVarsParser.ProgramContext ctx) {
        List<ProcDeclaration> procDeclarations = new ArrayList<>();
        for(TinyVarsParser.Proc_declareContext p : ctx.proc_declare()) {
            procDeclarations.add(this.visitProc_declare(p));
        }
        List<Statement> statements = new ArrayList<>();
        for(TinyVarsParser.StatementContext s : ctx.statement()) {
          statements.add(this.visitStatement(s));
        }
        return new Program(procDeclarations, statements);
    }

    @Override
    public Statement visitStatement(TinyVarsParser.StatementContext ctx) {

        if(ctx.declare() != null) {
            return visitDeclare(ctx.declare());
        } else if (ctx.assign() != null) {
            return visitAssign(ctx.assign());
        } else if (ctx.print() != null) {
            return visitPrint(ctx.print());
        } else if (ctx.call() != null) {
            return visitCall(ctx.call());
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

    @Override
    public Print visitPrint(TinyVarsParser.PrintContext ctx) {
        return new Print(visitExp(ctx.exp()));
    }

    @Override
    public Exp visitExp(TinyVarsParser.ExpContext ctx) {
        if(ctx.varname() != null) {
            return visitVarname(ctx.varname());
        } else if (ctx.CONST() != null) {
            return new Number(Integer.parseInt(ctx.CONST().getText()));
        } else {
            throw new RuntimeException("Expression parse tree with invalid context information");
        }
    }

    @Override
    public Name visitVarname(TinyVarsParser.VarnameContext ctx) {
        return new Name(ctx.NAME().getText());
    }

}
