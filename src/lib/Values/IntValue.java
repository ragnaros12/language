package lib.Values;

import lib.Types;

public class IntValue implements Value {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value + "";
    }

    @Override
    public int asInt() {
        return value;
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public int getType() {
        return Types.INTEGER;
    }

    public boolean asBool() {
        switch (value){
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new RuntimeException("тип не может быть превращен в bool");
        }
    }
}
