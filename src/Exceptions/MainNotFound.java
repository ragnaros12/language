package Exceptions;

public class MainNotFound extends RuntimeException{
    public MainNotFound() {
        super("точка входа main не найдена");
    }
}
