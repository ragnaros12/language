package ast.Statements;

import lib.Variable;
import lib.Variables;

import java.util.ArrayList;

public class BlockStatement implements Statement {
    public ArrayList<Statement> statements;
    private boolean IsNewStack;

    public BlockStatement(boolean IsNewStack) {
        statements = new ArrayList<>();
        this.IsNewStack = IsNewStack;
    }

    public void setNewStack(boolean newStack) {
        IsNewStack = newStack;
    }

    public void add(Statement statement){
        statements.add(statement);
    }
    public void add(Statement statement,int s){
        statements.add(s,statement);
    }
    public void addToFirst(Statement statement){
        statements.add(0,statement);
    }

    @Override
    public void execute() {
        if(IsNewStack)
            Variables.AddStack();
        for (Statement statement : statements){
            statement.execute();
        }
        if(IsNewStack)
            Variables.RemoveTopLevel();
    }
}
