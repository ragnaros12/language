package Parser;

public class Token {
    private String text;
    private TokenType type;

    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }

    public Token() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + text;
    }
}
