package ast.evaluators;

import ast.*;
import ast.Number;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluator implements TinyVarsVisitor<StringBuilder,Integer> {
    // Map to store our current variable assignments
    // (note that we can only use names as keys here because we have implemented hashCode for them based on comparing the strings they contain!)
    private final Map<Name, Integer> symbolTable = new HashMap<>();
    // Map that contains the currently used memory locations
    private final Map<Integer, Integer> memory = new HashMap<>();
    private int nextLocation = 1;
    // Real or simulated list of command line arguments passed to our program
    private final List<Integer> commandLineArgs;

    public Evaluator(List<Integer> commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    @Override
    public Integer visit(StringBuilder errors, Program p) {
        for (Statement s : p.getStatements()) {
            s.accept(errors,this);
            if(!errors.isEmpty()) {
                break; // terminate evaluation if we have an error already
            }
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Dec d) {
        symbolTable.put(d.getName(), nextLocation);
        nextLocation++;
        return null; // no return value for statements
    }

    @Override
    public Integer visit(StringBuilder errors, Set s) {
        Integer result = s.getExp().accept(errors,this);

        if(!symbolTable.containsKey(s.getName())) { // check for declaration
            errors.append("Attempt to assign variable ").append(s.getName()).append(" that wasn't declared.\n");
        }
        if(errors.isEmpty()){ // if we haven't already generated errors either from s.getExp() or from the if condition above
            Integer location = symbolTable.get(s.getName());
            memory.put(location, result);
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
        if(!symbolTable.containsKey(name)) { // check for declaration
            errors.append("Attempt to use variable ").append(name).append(" that wasn't declared.\n");
        } else {
            Integer location = symbolTable.get(name);
            if(!memory.containsKey(location)) {
                errors.append("Attempt to use (read) variable ").append(name).append(" that wasn't assigned a value.\n");
            } else {
                return memory.get(location);
            }
        }
        return null; // dummy value; only returned in case we generated an error
    }

    @Override
    public Integer visit(StringBuilder errors, Undef n) {
        Integer location = symbolTable.remove(n.getName());
        if (location == null) {
            errors.append("Attempt to undefine variable ").append(n.getName()).append(" that wasn't declared.\n");
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Alias n) {
        if(!symbolTable.containsKey(n.getAlias())) { // check for alias declaration (this check is optional)
            errors.append("Attempt to create alias ").append(n.getAlias()).append(" that wasn't declared.\n");
        }
        if(!symbolTable.containsKey(n.getForName())) { // check for variable declaration (this check is important)
            errors.append("Attempt to create alias for variable ").append(n.getForName()).append(" that wasn't declared.\n");
        }
        if(errors.isEmpty()) { // if we haven't already generated errors either from s.getExp() or from the if condition above
            Integer location = symbolTable.get(n.getForName());
            symbolTable.put(n.getAlias(), location);
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Conditional n) {
        Integer condResult = n.getCondition().accept(errors,this);
        if (!errors.isEmpty() || condResult == null)
            return null;
        if (condResult != 0) {
            return n.getIfBlock().accept(errors, this);
        }
        else {
            return n.getElseBlock().accept(errors, this);
        }
    }

    @Override
    public Integer visit(StringBuilder errors, Block n) {
        for (Statement s : n.getStatements()) {
            s.accept(errors,this);
            if(!errors.isEmpty()) {
                break;
            }
        }
        return null;
    }

    @Override
    public Integer visit(StringBuilder errors, Number n) {
        return n.getValue();
    }

    @Override
    public Integer visit(StringBuilder errors, CommandLineArg n) {
        if (commandLineArgs.size() <= n.getIndex()) {
            errors.append("Attempt to access command line argument at index ").append(n.getIndex()).append(" that does not exist.\n");
            return null;
        }
        return commandLineArgs.get(n.getIndex());
    }

    public Map<Name, Integer> getSymbolTable() {
        return symbolTable;
    }

    public Map<Integer, Integer> getMemory() {
        return memory;
    }
}
