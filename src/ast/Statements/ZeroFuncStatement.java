package ast.Statements;

import ast.Expressions.Expression;
import lib.Function;

public class ZeroFuncStatement implements Statement{
    Expression expression;

    public ZeroFuncStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        expression.eval();
    }
}
