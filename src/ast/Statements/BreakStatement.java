package ast.Statements;

public class BreakStatement extends RuntimeException implements Statement{
    @Override
    public void execute() {
        throw this;
    }
}
