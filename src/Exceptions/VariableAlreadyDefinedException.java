package Exceptions;

public class VariableAlreadyDefinedException extends RuntimeException{
    public VariableAlreadyDefinedException() {
        super("переменная уже существует");
    }
}
