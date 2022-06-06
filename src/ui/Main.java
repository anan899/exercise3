package ui;

import ast.*;
import ast.evaluators.Evaluator;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.ParseTreeToAST;
import parser.TinyVarsLexer;
import parser.TinyVarsParser;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, MismatchedTokenException {
        TinyVarsLexer lexer = new TinyVarsLexer(CharStreams.fromFileName("/Users/touhirokazu/Exercise2-main/TinyVars-solution/input.tvar"));
        for (Token token : lexer.getAllTokens()) {
            System.out.println(token);
        }
        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);
        System.out.println("Done tokenizing");

        TinyVarsParser parser = new TinyVarsParser(tokens);
        ParseTreeToAST visitor = new ParseTreeToAST();
        Program parsedProgram = visitor.visitProgram(parser.program());
        System.out.println("Done parsing");

        Evaluator e = new Evaluator();
        StringBuilder s = new StringBuilder();
        parsedProgram.accept(s, e);
        if(s.isEmpty()) {
            System.out.println("Evaluation completed successfully");
        } else {
            System.out.println("Error during runtime: \n" + s);
        }
        System.out.println("Final symbol table:" + e.getSymbolTable());
    }

}