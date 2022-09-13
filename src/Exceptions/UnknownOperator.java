package Exceptions;

import Parser.Token;
import Parser.TokenType;

public class UnknownOperator extends RuntimeException{
    public UnknownOperator(Token curr) {
        super("неизвестный оператор " + curr.getType() + " " + curr.getText());
    }
}
