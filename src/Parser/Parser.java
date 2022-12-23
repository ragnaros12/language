package Parser;

import Exceptions.TokenNotMatch;
import Exceptions.UnknownExpressionException;
import Exceptions.UnknownOperator;
import ast.Expressions.*;
import ast.Statements.*;
import lib.Types;
import lib.Values.VoidValue;
import java.util.ArrayList;

public class Parser {
    public static Token EOF = new Token("",TokenType.EOF);
    private final ArrayList<Token> tokens;
    private int pos;
    private final int size;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        pos = 0;
        size = tokens.size();
    }

    public BlockStatement Parse(){
        BlockStatement result = new BlockStatement(false);
        while (!match(TokenType.EOF)){
            result.add(statement());
        }
        return result;
    }


    private Statement statement(){
        return VarAndFunc();
    }
    private Statement VarAndFunc(){
        Token curr = get(0);
        int ind = Types.names.indexOf(curr.getText());

        if(ind != -1 || curr.getType() == TokenType.VOID) {
            if(curr.getType() == TokenType.VOID || get(2).getType() == TokenType.L_PAREN){
                int retType;
                if(curr.getType() == TokenType.VOID) {
                    retType = Types.VOID;
                    consume(TokenType.VOID);
                }
                else {
                    retType = ind;
                    consume(TokenType.WORD);
                }

                String name = consume(TokenType.WORD).getText();

                consume(TokenType.L_PAREN);

                ArrayList<Statement> varStatements = new ArrayList<>();
                ArrayList<Integer> types = new ArrayList<>();

                while (!match(TokenType.R_PAREN)){
                    BlockStatement statement = (BlockStatement) DefVar(get(0));
                    types.add(((VarStatement)statement.statements.get(0)).getType());
                    varStatements.add(statement);
                    match(TokenType.SEMI);
                }

                BlockStatement block = (BlockStatement)block();
                for (Statement statement : varStatements){
                    block.addToFirst(statement);
                }
                int[] retTypes = new int[types.size()];
                for (int i = 0; i < retTypes.length; i++){
                    retTypes[i] = types.get(i);
                }
                return new FunctionStatement(retType, block, name, retTypes);

            }
            else
                return DefVar(ind);
        }
        return BaseOperators();
    }
    private Statement BaseOperators(){
        Token curr = get(0);
        if(curr.getType() == TokenType.IF){
            Statement If;
            Statement Else = null;
            consume(TokenType.IF);
            consume(TokenType.L_PAREN);
            Expression expression = expression();
            consume(TokenType.R_PAREN);
            If = block(false);
            if(match(TokenType.ELSE))
                Else = block(false);
            return new IfStatement(expression, If, Else);
        }
        if(match(TokenType.RETURN)){
            Expression expression;
            try{
                expression = expression();
            }
            catch (Exception e){
                expression = VoidValue::new;
            }
            return new ReturnStatement(expression);
        }
        if(match(TokenType.BREAK)){
            return new BreakStatement();
        }
        if(match(TokenType.CONTINUE)){
            return new ContinueStatement();
        }
        if(curr.getType() == TokenType.WHILE){
            consume(TokenType.WHILE);
            consume(TokenType.L_PAREN);
            Expression expression = expression();
            consume(TokenType.R_PAREN);
            Statement block = block();
            return new WhileStatement(block, expression);

        }
        if(curr.getType() == TokenType.FOR){
            BlockStatement ret = new BlockStatement(true);
            consume(TokenType.FOR);
            consume(TokenType.L_PAREN);
            Statement init = VarAndFunc();
            consume(TokenType.SEMI);
            Expression If = expression();
            consume(TokenType.SEMI);
            Statement next = BaseOperators();
            consume(TokenType.R_PAREN);
            Statement block = block(false);
            ret.add(new ForStatement(init, If, next, block));
            return ret;
        }
        return Assignment();

    }
    private Statement Assignment(){
        Token curr = get(0);
        if(curr.getType() == TokenType.WORD){
            if(get(1).getType() == TokenType.ASSIGNMENT) {
                String variable = curr.getText();
                consume(TokenType.WORD);
                consume(TokenType.ASSIGNMENT);
                return new AssignmentStatement(variable, expression(), "=");
            }
            else if(get(1).getType() == TokenType.EQ_PLUS){
                String variable = curr.getText();
                consume(TokenType.WORD);
                consume(TokenType.EQ_PLUS);
                return new AssignmentStatement(variable, expression(), "+=");
            }
            else if(get(1).getType() == TokenType.EQ_MINUS){
                String variable = curr.getText();
                consume(TokenType.WORD);
                consume(TokenType.EQ_MINUS);
                return new AssignmentStatement(variable, expression(), "-=");
            }
            if(get(1).getType() == TokenType.PLUS_PLUS || get(1).getType() == TokenType.MINUS_MINUS) {
                consume(TokenType.WORD);
                if(match(TokenType.PLUS_PLUS))
                    return new PostUnaryStatement(curr.getText(), "++");
                if (match(TokenType.MINUS_MINUS))
                    return new PostUnaryStatement(curr.getText(), "--");
            }
        }
        if(curr.getType() == TokenType.WORD && get(1).getType() == TokenType.L_PAREN){
            return NonArgsFunc();
        }
        throw new UnknownOperator(curr);
    }


    private Statement NonArgsFunc(){
        return new ZeroFuncStatement(function());
    }
    private Statement DefVar(int ind){
        BlockStatement res = new BlockStatement(false);
        consume(TokenType.WORD);
        res.add(new VarStatement(get(0).getText(), ind));
        Token token = get(1);
        if (token.getType() == TokenType.ASSIGNMENT)
            res.add(Assignment());
        else
            consume(TokenType.WORD);
        return res;
    }
    private Statement DefVar(Token curr){
        int ind = Types.names.indexOf(curr.getText());
        BlockStatement res = new BlockStatement(false);
        consume(TokenType.WORD);
        Token name = get(0);
        res.add(new VarStatement(name.getText(), ind));
        Token token = get(1);
        if (token.getType() == TokenType.ASSIGNMENT)
            res.add(Assignment());
        else {
            res.add(new AssignmentValueStatement(name.getText(), Types.zero.get(ind)));
            consume(TokenType.WORD);
        }
        return res;
    }
    private Statement block(){
        return block(true);
    }
    private BlockStatement block(boolean isNewStack){
        BlockStatement statement = new BlockStatement(isNewStack);
        if(match(TokenType.OPEN_FIGURE)) {
            while (!match(TokenType.CLOSE_FIGURE)) {
                statement.add(statement());
            }
        }
        else{
            statement.add(statement());
        }
        return statement;
    }


    private Expression function(){
        String name = consume(TokenType.WORD).getText();
        consume(TokenType.L_PAREN);
        ArrayList<Expression> args = new ArrayList<>();
        while (!match(TokenType.R_PAREN)){
            args.add(expression());
            match(TokenType.SEMI);
        }
        return new FunctionExpression(name, args);
    }
    private Expression expression(){
        return AndOrOr();
    }
    private Expression AndOrOr(){
        Expression res = conditional();
        while (true) {
            if (match(TokenType.AND)) {
                res = new ConditionalExpression(res, conditional(), "&&");
                break;
            }
            if (match(TokenType.OR)) {
                res = new ConditionalExpression(res, conditional(), "||");
                break;
            }
            break;
        }
        return res;
    }
    private Expression conditional(){
        Expression res = additive();

        while (true){
            if(match(TokenType.EQ)){
                res = new ConditionalExpression(res, additive(), "==");
                break;
            }
            if(match(TokenType.GT)){
                res = new ConditionalExpression(res, additive(), ">");
                break;
            }
            if(match(TokenType.LT)){
                res = new ConditionalExpression(res, additive(), "<");
                break;
            }
            if(match(TokenType.EQ_OR_G)){
                res = new ConditionalExpression(res, additive(), ">=");
                break;
            }
            if(match(TokenType.EQ_OR_L)){
                res = new ConditionalExpression(res, additive(), "<=");
                break;
            }
            if(match(TokenType.NOT_EQ)){
                res = new ConditionalExpression(res, additive(), "!=");
                break;
            }
            break;
        }
        return res;
    }
    private Expression additive(){
        Expression res = multiplicative();

        while (true){
            if(match(TokenType.PLUS)){
                res =  new BinaryExpression(res, multiplicative(), '+');
                continue;
            }
            if(match(TokenType.MINUS)){
                res = new BinaryExpression(res, multiplicative(), '-');
                continue;
            }
            break;
        }
        return res;
    }
    private Expression multiplicative(){
        Expression res = unary();

        while (true){
            if(match(TokenType.STAR)){
                res =  new BinaryExpression(res, unary(), '*');
                continue;
            }
            if(match(TokenType.SLASH)){
                res = new BinaryExpression(res, unary(), '/');
                continue;
            }
            break;
        }
        return res;
    }
    private Expression unary(){
        if(match(TokenType.MINUS)){
            return new UnaryExpression(primary(), '-');
        }
        else if(match(TokenType.EX_MARK)){
            return new UnaryExpression(primary(), '!');
        }
        else if(match(TokenType.PLUS)){
            return primary();
        }
        return primary();
    }
    private Expression primary(){
        Token curr = get(0);
        if(match(TokenType.INT_NUMBER)){
            return new NumberExpression(Integer.parseInt(curr.getText()));
        }
        if(match(TokenType.HEX_NUMBER)){
            return new NumberExpression(Long.parseLong(curr.getText(), 16));
        }
        if(get(0).getType() == TokenType.WORD && get(1).getType() == TokenType.L_PAREN){
            return function();
        }
        if(match(TokenType.DOUBLE_NUMBER)){
            return new NumberExpression(Double.parseDouble(curr.getText()));
        }
        if(match(TokenType.STRING_VALUE)){
            return new StringExpression(curr.getText());
        }
        if(match(TokenType.TRUE)){
            return new BooleanExpression(true);
        }
        if(match(TokenType.FALSE)){
            return new BooleanExpression(false);
        }
        if(match(TokenType.WORD)){
            return new VariableExpression(curr.getText());
        }
        if(match(TokenType.L_PAREN)){
            Expression res = expression();
            match(TokenType.R_PAREN);
            return res;
        }
        throw new UnknownExpressionException();
    }


    private Token consume(TokenType type){
        Token curr = get(0);
        if(type != curr.getType()) throw new TokenNotMatch(curr.getType(), type);
        pos++;
        return curr;
    }
    private boolean match(TokenType type){
        Token curr = get(0);
        if(type != curr.getType()) return false;
        pos++;
        return true;
    }
    private Token get(int Relative){
        int position = pos + Relative;
        if(position >= size) return EOF;
        return tokens.get(position);
    }
}
