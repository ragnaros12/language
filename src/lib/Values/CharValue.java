package lib.Values;

import lib.Types;

public class CharValue implements Value{
    @Override
    public String asString() {
        return null;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public boolean asBool() {
        return false;
    }

    @Override
    public int getType() {
        return Types.CHAR;
    }
}
