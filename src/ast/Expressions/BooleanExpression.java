package ast.Expressions;

import lib.Values.BooleanValue;
import lib.Values.Value;

public class BooleanExpression implements Expression{
    private Value value;

    public BooleanExpression(boolean value) {
        this.value = new BooleanValue(value);
    }

    @Override
    public Value eval() {
        return value;
    }
}
