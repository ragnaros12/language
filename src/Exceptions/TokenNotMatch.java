package Exceptions;

import Parser.Token;
import Parser.TokenType;

public class TokenNotMatch extends RuntimeException{
    public TokenNotMatch(TokenType token, TokenType token1) {
        super("токен " + token + " не соответсвует " + token1);
    }
}
