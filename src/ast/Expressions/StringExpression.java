package ast.Expressions;

import lib.Values.StringValue;
import lib.Values.Value;

public class StringExpression implements Expression {
    private Value value;

    public StringExpression(String str){
        value = new StringValue(str);
    }

    @Override
    public Value eval() {
        return value;
    }
}
