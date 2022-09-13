package Exceptions;

public class TypeException extends RuntimeException{

    public TypeException(){
        super("несовместимость типов");
    }
}
