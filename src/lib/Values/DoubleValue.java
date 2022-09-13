package lib.Values;

import lib.Types;

public class DoubleValue implements Value {
    private double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }

    @Override
    public int asInt() {
        return (int)value;
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public boolean asBool() {
        switch ((int)value){
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new RuntimeException("тип не может быть превращен в bool");
        }
    }

    @Override
    public int getType() {
        return Types.DOUBLE;
    }

}
