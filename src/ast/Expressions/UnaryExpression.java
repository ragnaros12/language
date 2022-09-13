package ast.Expressions;

import ast.Expressions.Expression;
import lib.Values.BooleanValue;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Types;
import lib.Values.Value;

public class UnaryExpression implements Expression {
    private final Expression expr;
    private final char operation;

    public UnaryExpression(Expression expr, char operation) {
        this.expr = expr;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        Value exp = expr.eval();
        switch (exp.getType()) {
            case Types.BOOLEAN:
                switch (operation){
                    case '!':
                        return new BooleanValue(!exp.asBool());
                }
            case Types.INTEGER:
                switch (operation) {
                    case '!':
                    case '-':
                        return new IntValue(-exp.asInt());
                    case '+':
                        return expr.eval();
                }
            case Types.DOUBLE :
                switch (operation) {
                    case '-':
                    case '!':
                        return new DoubleValue(-exp.asInt());
                    case '+':
                        return expr.eval();
                }
            case Types.STRING:
                throw new RuntimeException("тип string не может использовать унарные операторы");
        }
        throw new RuntimeException("такого типа не существует");
    }
}
