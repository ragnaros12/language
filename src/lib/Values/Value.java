package lib.Values;

public interface Value {
    public String asString();
    public int asInt();
    public double asDouble();
    public boolean asBool();
    int getType();
}
