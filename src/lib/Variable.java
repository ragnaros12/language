package lib;

import lib.Values.Value;

public class Variable {
    private final String name;
    private Value value;
    private final int type;
    private final int StackPos;

    public Variable(String name, Value value, int type, int stackPos) {
        this.name = name;
        this.value = value;
        this.type = type;
        StackPos = stackPos;
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public int getStackPos() {
        return StackPos;
    }
}
