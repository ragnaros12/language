package ast.Statements;

public class ContinueStatement extends RuntimeException implements Statement{
    @Override
    public void execute() {
        throw this;
    }
}
