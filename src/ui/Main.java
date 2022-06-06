package ui;

import ast.*;
import ast.evaluators.Evaluator;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.ParseTreeToAST;
import parser.TinyVarsLexer;
import parser.TinyVarsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        TinyVarsLexer lexer = new TinyVarsLexer(CharStreams.fromFileName("input.tvar"));
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

        // Added passing of command line arguments
        List<Integer> commandLineArgs = new ArrayList<>();
        for (String arg : args) {
            commandLineArgs.add(Integer.parseInt(arg));
        }

        Evaluator e = new Evaluator(commandLineArgs);
        StringBuilder s = new StringBuilder();
        parsedProgram.accept(s, e);
        if(s.isEmpty()) {
            System.out.println("Evaluation completed successfully");
        } else {
            System.out.println("Error during runtime: \n" + s);
        }
        System.out.println("Final symbol table:" + e.getSymbolTable());
        System.out.println("Final memory:" + e.getMemory());

    }

}