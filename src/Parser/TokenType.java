package Parser;

public enum TokenType {

    STAR,
    SLASH,
    PLUS,
    LT,
    GT,
    MINUS,
    TRUE,
    FALSE,
    RETURN,
    CONTINUE,
    BREAK,
    EX_MARK,
    ASSIGNMENT,


    VOID,



    OPEN_FIGURE,
    CLOSE_FIGURE,

    EQ,
    EQ_PLUS,
    EQ_MINUS,
    MINUS_MINUS,
    PLUS_PLUS,
    EQ_OR_G,
    NOT_EQ,
    EQ_OR_L,

    AND,
    OR,

    INT,
    DOUBLE,

    SEMI,

    L_PAREN, //(
    R_PAREN, //)


    IF,
    ELSE,
    FOR,
    WHILE,

    INT_NUMBER,
    HEX_NUMBER,
    WORD,
    DOUBLE_NUMBER,
    STRING_VALUE,


    EOF,
    NONE_TYPE
}
