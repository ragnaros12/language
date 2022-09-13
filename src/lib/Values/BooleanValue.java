package lib.Values;

import lib.Types;

public class BooleanValue implements Value{
    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }

    @Override
    public int asInt() {
        return value ? 1 : 0;
    }

    @Override
    public double asDouble() {
        return value ? 1 : 0;
    }

    @Override
    public boolean asBool() {
        return value;
    }

    @Override
    public int getType() {
        return Types.BOOLEAN;
    }
}
