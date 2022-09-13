package ast.Expressions;

import lib.Types;
import lib.Values.BooleanValue;
import lib.Values.Value;

public class ConditionalExpression implements Expression{
    private Expression expr1;
    private Expression expr2;
    private String op;

    public ConditionalExpression(Expression expr1, Expression expr2, String op) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;
    }

    @Override
    public Value eval() {
        Value value = expr1.eval();
        Value value1 = expr2.eval();
        if((value.getType() == Types.INTEGER || value1.getType() == Types.DOUBLE) && (value.getType() == Types.DOUBLE || value1.getType() == Types.INTEGER)){
            switch (op){
                case ">" :
                    return new BooleanValue(value.asDouble() > value1.asDouble());
                case "<":
                    return new BooleanValue(value.asDouble() < value1.asDouble());
                case "<=":
                    return new BooleanValue(value.asDouble() <= value1.asDouble());
                case ">=":
                    return new BooleanValue(value.asDouble() >= value1.asDouble());
                case "!=":
                    return new BooleanValue(value.asDouble() != value1.asDouble());
                case "==":
                    return new BooleanValue(value.asDouble() == value1.asDouble());
            }
        }
        else if(value.getType() == Types.STRING && value1.getType() == Types.STRING){
            switch (op){
                case "==":
                    return new BooleanValue(value.asString().equals(value1.asString()));
                case "!=":
                    return new BooleanValue(!value.asString().equals(value1.asString()));
                default:
                    throw new RuntimeException("");
            }
        }

        if(op.equals("&&")){
            return new BooleanValue(value.asBool() && value1.asBool());
        }
        else if(op.equals("||")){
            return new BooleanValue(value.asBool() || value1.asBool());
        }

        throw new RuntimeException("нет такого типа");
    }
}
