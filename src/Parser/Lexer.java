package Parser;

import java.util.*;

public final class Lexer {
    private final String input;
    private final ArrayList<Token> tokens;
    private int pos;
    private final int length;


    private static final ArrayList<Map.Entry<String, TokenType>> OPERATORS = new ArrayList<>();
    private static final ArrayList<Character> SkipChars = new ArrayList<>();

    static {
        OPERATORS.add(new AbstractMap.SimpleEntry<>("+", TokenType.PLUS));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("-", TokenType.MINUS));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("*", TokenType.STAR));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("/", TokenType.SLASH));


        OPERATORS.add(new AbstractMap.SimpleEntry<>("{", TokenType.OPEN_FIGURE));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("}", TokenType.CLOSE_FIGURE));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("(", TokenType.L_PAREN));
        OPERATORS.add(new AbstractMap.SimpleEntry<>(")", TokenType.R_PAREN));

        OPERATORS.add(new AbstractMap.SimpleEntry<>(";", TokenType.SEMI));

        OPERATORS.add(new AbstractMap.SimpleEntry<>("=", TokenType.ASSIGNMENT));

        OPERATORS.add(new AbstractMap.SimpleEntry<>("<", TokenType.LT));
        OPERATORS.add(new AbstractMap.SimpleEntry<>(">", TokenType.GT));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("!", TokenType.EX_MARK));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("==", TokenType.EQ));
        OPERATORS.add(new AbstractMap.SimpleEntry<>(">=", TokenType.EQ_OR_G));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("<=", TokenType.EQ_OR_L));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("!=", TokenType.NOT_EQ));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("+=", TokenType.EQ_PLUS));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("-=", TokenType.EQ_MINUS));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("++", TokenType.PLUS_PLUS));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("--", TokenType.MINUS_MINUS));

        OPERATORS.add(new AbstractMap.SimpleEntry<>("&&", TokenType.AND));
        OPERATORS.add(new AbstractMap.SimpleEntry<>("||", TokenType.OR));

        SkipChars.add(' ');
        SkipChars.add('\r');
        SkipChars.add('\n');
        SkipChars.add('\t');
    }


    public Lexer(String input) {
        this.input = input;
        length = input.length();
        pos = 0;
        tokens = new ArrayList<>();
    }

    public ArrayList<Token> Tokenize(){
        while (pos < length){
            char curr = peek(0);
            if(SkipChars.contains(curr))
                Next();
            else if(Character.isDigit(curr))
                tokenizeNumber();
            else if(Character.isLetter(curr)) tokenizeWord();
            else if(curr == '#'){
                Next();
                tokenizeHexNumber();
            }
            else if(isOperator())
                tokenizeOperator();
            else if(curr == '"')
                tokenizeString();
            else
                throw new RuntimeException("неизвестный токен" + curr);
        }
        return tokens;
    }

    private void tokenizeWord(){
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true){
            if(!Character.isLetterOrDigit(current) && current != '_' && current != '$'){
                break;
            }
            buffer.append(current);
            current = Next();
        }
        String word = buffer.toString();
        switch (word){
            case "true":
                AddToken(TokenType.TRUE);
                break;
            case "false":
                AddToken(TokenType.FALSE);
                break;
            case "if":
                AddToken(TokenType.IF);
                break;
            case "else":
                AddToken(TokenType.ELSE);
                break;
            case "while":
                AddToken(TokenType.WHILE);
                break;
            case "for":
                AddToken(TokenType.FOR);
                break;
            case "void":
                AddToken(TokenType.VOID);
                break;
            case "return":
                AddToken(TokenType.RETURN);
                break;
            case "break":
                AddToken(TokenType.BREAK);
                break;
            case "continue":
                AddToken(TokenType.CONTINUE);
                break;
            default:
                AddToken(TokenType.WORD, buffer.toString());
                break;
        }
    }
    private void tokenizeHexNumber(){
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (Character.isDigit(current) || isHex(Character.toLowerCase(current))){
            buffer.append(current);
            current = Next();
        }
        AddToken(TokenType.HEX_NUMBER, buffer.toString());
    }
    private void tokenizeString(){
        Next();
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (current != '"'){
            if(current == '\\'){
                current = Next();
                switch (current){
                    case '"' : current = Next(); buffer.append('"'); continue;
                    case 'n' : current = Next(); buffer.append('\n'); continue;
                    case 'r' : current = Next(); buffer.append('\r'); continue;
                    case 't' : current = Next(); buffer.append('\t'); continue;
                }
            }
            buffer.append(current);
            current = Next();
        }
        String word = buffer.toString();
        AddToken(TokenType.STRING_VALUE, word);
        Next();
    }
    private void tokenizeNumber(){
        StringBuilder buffer = new StringBuilder();
        char current = peek(0);
        while (true){
            if(current == '.'){
                if(buffer.indexOf(".") != -1) throw new RuntimeException("Invalid double number");
            }
            else if (!Character.isDigit(current)){
                break;
            }
            buffer.append(current);
            current = Next();
        }
        if(buffer.toString().indexOf('.') == -1)
            AddToken(TokenType.INT_NUMBER, buffer.toString());
        else
            AddToken(TokenType.DOUBLE_NUMBER, buffer.toString());
    }
    private void tokenizeOperator(){
        TokenType type = TokenType.NONE_TYPE;
        String key = "";
        for (Map.Entry<String, TokenType> str : OPERATORS){
            String peek = peek_length(str.getKey().length());
            if(peek.equals(str.getKey())) {
                type = str.getValue();
                key = str.getKey();
            }
        }
        AddToken(type);
        Next(key.length());
    }


    private boolean isOperator(){
        for (Map.Entry<String, TokenType> str : OPERATORS){
            String peek = peek_length(str.getKey().length());
            if(peek.equals(str.getKey())) {
                return true;
            }
        }
        return false;
    }
    private boolean isHex(char c) {
        return "abcdef".indexOf(c) != -1;
    }
    private char peek(int Relative){
        int position = pos + Relative;
        if(position >= length) return '\0';
        return input.charAt(position);
    }
    private String peek_length(int Relative){
        if(pos + Relative > length) return "\0";
        return input.substring(pos, Relative + pos);
    }
    private void Next(int length){
        for (int i = 0; i < length; i++)
            Next();
    }
    private char Next(){
        pos++;
        return  peek(0);
    }
    private void AddToken(TokenType type){
        tokens.add(new Token("", type));
    }
    private void AddToken(TokenType type, String text){
        tokens.add(new Token(text, type));
    }
}
