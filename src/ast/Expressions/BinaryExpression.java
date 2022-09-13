package ast.Expressions;

import ast.Expressions.Expression;
import lib.*;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Values.StringValue;
import lib.Values.Value;

public class BinaryExpression implements Expression {
    private Expression expr1, expr2;
    private char operation;

    public BinaryExpression(Expression expr1, Expression expr2, char operation) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operation = operation;
    }

    @Override
    public Value eval() {

        Value expr1v = expr1.eval();
        Value expr2v = expr2.eval();

        int type1 = expr1v.getType();
        int type2 = expr2v.getType();

        if(type1 == Types.INTEGER && type2 == Types.INTEGER) {
            switch (operation) {
                case '-':
                    int res = expr1v.asInt() - expr2v.asInt();
                    return new IntValue(res);
                case '*':
                    res = expr1v.asInt() * expr2v.asInt();
                    return new IntValue(res);
                case '/':
                    res = expr1v.asInt() / expr2v.asInt();
                    return new IntValue(res);
                case '+':
                    res = expr1v.asInt() + expr2v.asInt();
                    return new IntValue(res);
            }
        }
        else if(type1 == Types.STRING && type2 == Types.STRING){
            if (operation == '+') {
                return new StringValue(expr1v.asString() + expr2v.asString());
            }
            throw new RuntimeException("данный бинарный оператор не может быть примене к типу string и string");
        }
        else if(type1 == Types.DOUBLE && type2 == Types.DOUBLE){
            switch (operation) {
                case '-':
                    double res = expr1v.asDouble() - expr2v.asDouble();
                    return new DoubleValue(res);
                case '*':
                    res = expr1v.asDouble() * expr2v.asDouble();
                    return new DoubleValue(res);
                case '/':
                    res = expr1v.asDouble() / expr2v.asDouble();
                    return new DoubleValue(res);
                case '+':
                    res = expr1v.asDouble() + expr2v.asDouble();
                    return new DoubleValue(res);
            }
        }
        else if((type1 == Types.DOUBLE || type2 == Types.DOUBLE) &&(type1 == Types.INTEGER || type2 == Types.INTEGER)){
            switch (operation) {
                case '-':
                    double res = expr1v.asDouble() - expr2v.asDouble();
                    return new DoubleValue(res);
                case '*':
                    res = expr1v.asDouble() * expr2v.asDouble();
                    return new DoubleValue(res);
                case '/':
                    res = expr1v.asDouble() / expr2v.asDouble();
                    return new DoubleValue(res);
                case '+':
                    res = expr1v.asDouble() + expr2v.asDouble();
                    return new DoubleValue(res);
            }
        }
        else if((type2 == Types.STRING)
                && (type1 == Types.INTEGER || type1 == Types.DOUBLE)){
            switch (operation) {
                case '*':
                    StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < expr1v.asInt(); i++)
                        buffer.append(expr1v.asString());
                    return new StringValue(buffer.toString());
                case '+':
                    return new StringValue( expr2v.asString()  + expr1v.asDouble());
            }
        }
        else if((type1 == Types.STRING)
                && (type2 == Types.INTEGER || type2 == Types.DOUBLE)){
            switch (operation) {
                case '*':
                    StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < expr2v.asInt(); i++)
                        buffer.append(expr1v.asString());
                    return new StringValue(buffer.toString());
                case '+':
                    return new StringValue(expr1v.asString() + expr2v.asDouble());
            }
        }
        throw new RuntimeException("неизестный тип");
    }
}
