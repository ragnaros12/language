package lib.Values;

public class VoidValue implements Value{
    @Override
    public String asString() {
        throw new RuntimeException("1");
    }

    @Override
    public int asInt() {
        throw new RuntimeException("1");
    }

    @Override
    public double asDouble() {
        throw new RuntimeException("1");
    }

    @Override
    public boolean asBool() {
        throw new RuntimeException("1");
    }

    @Override
    public int getType() {
        return -1;
    }
}
