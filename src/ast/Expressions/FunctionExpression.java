package ast.Expressions;

import lib.Function;
import lib.Functions;
import lib.Values.Value;
import lib.Variables;

import java.util.ArrayList;

public class FunctionExpression implements Expression{
    private String name;
    private ArrayList<Expression> args;

    public FunctionExpression(String name, ArrayList<Expression> args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public Value eval() {
        int[] types = new int[args.size()];
        Value[] values = new Value[args.size()];
        for (int i = 0; i < args.size(); i++){
            Value value = args.get(i).eval();
            values[i] = value;
            types[i] = value.getType();
        }
        Function function = Functions.get(name, types);
        return function.execute(values);
    }
}
