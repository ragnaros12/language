package lib;

import ast.Expressions.Expression;
import lib.Values.Value;

public abstract class Function {
    private String name;
    private int[] types;
    private int ReturnValue;

    public int getReturnValue() {
        return ReturnValue;
    }

    public void setReturnValue(int returnValue) {
        ReturnValue = returnValue;
    }

    public abstract Value execute(Value[] exprs);

    public String getName() {
        return name;
    }

    public Function(String name, int[] types, int returnValue) {
        this.name = name;
        this.types = types;
        ReturnValue = returnValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }
}
