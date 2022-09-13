package ast.Statements;

import Exceptions.VariableNotDefinedException;
import ast.Statements.Statement;
import lib.Types;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Values.Value;
import lib.Variables;

public class PostUnaryStatement implements Statement {
    String name;
    String operator;

    public PostUnaryStatement(String name, String operator) {
        this.name = name;
        this.operator = operator;
    }

    @Override
    public void execute() {
        Value expr = Variables.get(name);
        if(expr != null) {
            switch (operator) {
                case "++":
                    if (expr.getType() == Types.INTEGER)
                        Variables.put(name, new IntValue(expr.asInt() + 1));
                    if(expr.getType() == Types.DOUBLE)
                        Variables.put(name, new DoubleValue(expr.asDouble() + 1));
                    return;
                case "--":
                    if (expr.getType() == Types.INTEGER)
                        Variables.put(name, new IntValue(expr.asInt() - 1));
                    if(expr.getType() == Types.DOUBLE)
                        Variables.put(name, new DoubleValue(expr.asDouble() - 1));
                    return;
            }
        }
        throw new VariableNotDefinedException();
    }
}
