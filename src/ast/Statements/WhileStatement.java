package ast.Statements;

import ast.Expressions.Expression;

public class WhileStatement implements Statement {
    private final Statement statement;
    private final Expression expression;

    public WhileStatement(Statement statement, Expression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public void execute() {
        while (expression.eval().asBool()) {
            try {
                statement.execute();
            } catch (BreakStatement ignored) {
                return;
            }
            catch (ContinueStatement ignored){
            }
        }
    }
}
