package ast.Expressions;

import ast.Expressions.Expression;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Values.Value;

public class NumberExpression implements Expression {
    private Value value;

    public NumberExpression(double value) {
        this.value = new DoubleValue(value);
    }
    public NumberExpression(int value) {
        this.value = new IntValue(value);
    }
    @Override
    public Value eval() {
        return value;
    }
}
