package ast.Statements;

import ast.Expressions.Expression;
import lib.Values.Value;

public class ReturnStatement extends RuntimeException implements Statement{
    private Expression expression;
    private Value value;

    public ReturnStatement(Expression expression){
        this.expression = expression;
    }

    public ReturnStatement(Value expression){
        this.value = expression;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public void execute() {
        value = expression.eval();
        throw this;
    }
}
