package ast.Statements;

import lib.*;
import lib.Values.BooleanValue;
import lib.Values.DoubleValue;
import lib.Values.IntValue;
import lib.Values.StringValue;

public class VarStatement implements Statement{
    private final String name;
    private final int type;

    public VarStatement(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    @Override
    public void execute() {
        switch (type){
            case Types.INTEGER:
                Variables.add(name, new IntValue(0));
                break;
            case Types.DOUBLE:
                Variables.add(name, new DoubleValue(0));
                break;
            case Types.STRING:
                Variables.add(name, new StringValue(""));
                break;
            case Types.BOOLEAN:
                Variables.add(name, new BooleanValue(false));
                break;
        }
    }
}
