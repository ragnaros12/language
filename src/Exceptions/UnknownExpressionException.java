package Exceptions;

public class UnknownExpressionException extends RuntimeException {
    public UnknownExpressionException() {
        super("неизвестное выражение");
    }
}
