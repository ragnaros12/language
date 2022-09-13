package Exceptions;

public class VariableNotDefinedException extends RuntimeException{
    public VariableNotDefinedException() {
        super("переменная не обьявлена");
    }
}
