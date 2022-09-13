package ast.Statements;

import ast.Expressions.Expression;

public class ForStatement implements Statement{
    private final Statement init;
    private final Expression If;
    private final Statement next;
    private final Statement block;

    public ForStatement(Statement init, Expression anIf, Statement next, Statement block) {
        this.init = init;
        If = anIf;
        this.next = next;
        this.block = block;
    }

    @Override
    public void execute() {
        init.execute();
        while (If.eval().asBool()) {
            try {
                block.execute();
                next.execute();
            }
            catch (BreakStatement ignored){
                return;
            }
            catch (ContinueStatement e){
                next.execute();
            }
        }
    }
}
