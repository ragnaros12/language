package ast.Statements;

import ast.Expressions.Expression;
import lib.Types;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Values.StringValue;
import lib.Values.Value;
import lib.Variable;
import lib.Variables;

public class AssignmentStatement implements Statement {
    private final String name;
    private final Expression expression;
    private final String operation;

    public AssignmentStatement(String name, Expression Value, String operation) {
        this.name = name;
        expression = Value;
        this.operation = operation;
    }


    @Override
    public void execute() {
        switch (operation) {
            case "=":
                Variables.put(name, expression.eval());
                break;
            case "+=":
                Value value = Variables.get(name);
                Value value1 = expression.eval();
                if(value != null) {
                    if(value.getType() == Types.STRING && value1.getType() == Types.STRING)
                        Variables.put(name, new StringValue(value.asString() + value1.asString()));
                    else if(value.getType() == Types.INTEGER && value1.getType() == Types.INTEGER)
                        Variables.put(name, new IntValue(value.asInt() + value1.asInt()));
                    else if(value.getType() == Types.STRING && (value1.getType() == Types.INTEGER || value1.getType() == Types.DOUBLE))
                        Variables.put(name, new StringValue(value.asString() + value1.asString()));
                    else if(value1.getType() == Types.STRING && (value.getType() == Types.INTEGER || value.getType() == Types.DOUBLE))
                        Variables.put(name, new StringValue(value.asString() + value1.asString()));
                    else if((value.getType() == Types.DOUBLE) && (value1.getType() == Types.DOUBLE || value1.getType() == Types.INTEGER))
                        Variables.put(name, new DoubleValue(value.asDouble() + value1.asDouble()));
                    else
                        throw new RuntimeException("такого оператора невозможно применить");
                    break;
                }
                else
                    throw new RuntimeException("переменной не существует");
            case "-=":
                value = Variables.get(name);
                value1 = expression.eval();
                if(value != null) {
                    if(value.getType() == Types.INTEGER && value1.getType() == Types.INTEGER)
                        Variables.put(name, new IntValue(value.asInt() - value1.asInt()));
                    else if((value.getType() == Types.DOUBLE) && (value1.getType() == Types.DOUBLE || value1.getType() == Types.INTEGER))
                        Variables.put(name, new DoubleValue(value.asDouble() - value1.asDouble()));
                    else
                        throw new RuntimeException("такого оператора невозможно применить");
                    break;
                }
                else
                    throw new RuntimeException("переменной не существует");
        }
    }
}
