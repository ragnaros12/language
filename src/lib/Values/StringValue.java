package lib.Values;

import lib.Types;

public class StringValue implements Value {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(value);
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(value);
    }

    @Override
    public int getType() {
        return Types.STRING;
    }

    public boolean asBool() {
        switch (value){
            case "false":
                return false;
            case "true":
                return true;
            default:
                throw new RuntimeException("тип не может быть превращен в bool");
        }
    }

}
