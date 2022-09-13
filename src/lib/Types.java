package lib;

import lib.Values.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Types {
    public static final int INTEGER = 0, STRING = 1, DOUBLE = 2, BOOLEAN = 3, VOID = -1, CHAR = 4;

    public static ArrayList<String> names = new ArrayList<>(Arrays.asList("int","string","double", "bool", "char"));
    public static ArrayList<Value> zero = new ArrayList<>(Arrays.asList(new IntValue(0), new StringValue(""), new DoubleValue(0), new BooleanValue(false)));
}
