package lib;

import lib.Values.Value;

public class Variable {
    private final String name;
    private Value value;
    private final int type;

    public Variable(String name, Value value, int type) {
        this.name = name;
        this.value = value;
        this.type = type;
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

    @Override
    public String toString() {
        return name + " " + type + " " + value.asString();
    }
}
