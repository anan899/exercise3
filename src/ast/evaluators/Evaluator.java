package ast.evaluators;

import ast.*;
import ast.Number;
import ast.Set;

import java.util.*;

public class Evaluator implements TinyVarsVisitor<StringBuilder,Integer> {
    // Map to store our current variable assignments
    // (note that we can only use names as keys here because we have implemented hashCode for them based on comparing the strings they contain!)
    private final Map<Name, Integer> symbolTable = new HashMap<>();
    // Map to store our procedure definitions
    private final Map<Name, ProcDeclaration> procTable = new HashMap<>();

    @Override
    public Integer visit(StringBuilder errors, Program p) {
        for(ProcDeclaration proc : p.getProcs()) {
            proc.accept(errors, this);
        }
        for (Statement s : p.getStatements()) {
            s.accept(errors,this);
            if(!errors.isEmpty()) {
                break; // terminate evaluation if we have an error already
            }
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, ProcDeclaration p) {
        procTable.put(p.getName(), p);

        return null; // no return value for procedure declarations
    }

    @Override
    public Integer visit(StringBuilder errors, Dec d) {
        symbolTable.put(d.getName(), null); // declare variable (no initialisation yet; you might also prefer a default initialisation)
        return null; // no return value for statements
    }

    @Override
    public Integer visit(StringBuilder errors, Set s) {
        Integer result = s.getExp().accept(errors,this);

        if(!symbolTable.containsKey(s.getName())) { // check for declaration
            errors.append("Attempt to assign variable ").append(s.getName()).append(" that wasn't declared.\n");
        }
        if(errors.isEmpty()){ // if we haven't already generated errors either from s.getExp() or from the if condition above
            symbolTable.put(s.getName(), result);
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Print p) {
        Integer value = p.getPrinted().accept(errors, this);
        if(errors.isEmpty()) { // no error generated
            System.out.println("PRINTING: " + value);
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Name name) {
        if(!symbolTable.containsKey(name)) {
            errors.append("Attempt to use variable ").append(name).append(" that wasn't declared.\n");
        } else {
            if(symbolTable.get(name) == null) {
                errors.append("Attempt to use (read) variable ").append(name).append(" that wasn't assigned a value.\n");
            } else {
                return symbolTable.get(name);
            }
        }
        return null; // dummy value; only returned in case we generated an error
    }

    @Override
    public Integer visit(StringBuilder errors, Number n) {
        return n.getValue();
    }

    @Override
    public Integer visit(StringBuilder errors, Call c) {
        if (!procTable.containsKey(c.getName())) {
            errors.append("Attempt to call procedure ").append(c.getName()).append(" that is not declared.\n");
            return null;
        }

        ProcDeclaration p = procTable.get(c.getName());
        if (p.getParams().size() != c.getArgs().size()) {
            errors.append("Attempt to call procedure ").append(c.getName()).append(" with invalid number of arguments.\n");
            return null;
        }

        Map<Name, Integer> oldSymbolTable = new HashMap<>(symbolTable);
        symbolTable.clear();

        for (int i = 0;i < c.getArgs().size(); i++) {
            Integer argEvaluated = c.getArgs().get(i).accept(errors, this);
            symbolTable.put(p.getParams().get(i), argEvaluated);
        }

        for (Statement s: p.getBody()) {
            s.accept(errors, this);
        }

        // Slightly awkward to avoid removing from HashSet while iterating over it
        /* java.util.Set<Name> names = new HashSet<>(symbolTable.keySet());
        for (Name name : names) {
            if (!oldSymbolTable.containsKey(name)) {
                symbolTable.remove(name);
            }
        }*/
        symbolTable.clear();
        symbolTable.putAll(oldSymbolTable);
        return null;
    }

    public Map<Name, Integer> getSymbolTable() {
        return symbolTable;
    }
}

