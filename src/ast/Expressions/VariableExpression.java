package ast.Expressions;

import lib.Values.Value;
import lib.Variable;
import lib.Variables;

public class VariableExpression implements Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        Value var = Variables.get(name);
        if(var != null) {
            return var;
        }
        throw new RuntimeException("переменной не существует");
    }
}
