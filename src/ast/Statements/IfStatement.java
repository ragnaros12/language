package ast.Statements;

import ast.Expressions.Expression;
import ast.Statements.Statement;
import lib.Types;
import lib.Values.Value;

import java.util.ArrayList;

public class IfStatement implements Statement {
    private final Expression expr;
    private final Statement If;
    private final Statement Else;

    public IfStatement(Expression expr, Statement anIf, Statement Else) {
        this.expr = expr;
        If = anIf;
        this.Else = Else;
    }

    @Override
    public void execute() {
        Value value = expr.eval();
        if(value.asBool()) {
            If.execute();
        }
        else{
            if(Else != null)
                Else.execute();
        }
    }
}
