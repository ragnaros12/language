package lib;

import Exceptions.TypeException;
import Exceptions.VariableAlreadyDefinedException;
import Exceptions.VariableNotDefinedException;
import ast.Expressions.Expression;
import lib.Values.StringValue;
import lib.Values.Value;
import lib.Values.VoidValue;

import java.util.ArrayList;
import java.util.Arrays;

public class Functions {
    public static ArrayList<Function> Functions;

    static  {
        Functions = new ArrayList<>();
        Functions.add(new Function("print", new int[]{Types.STRING}, Types.VOID) {
            @Override
            public Value execute(Value[] values) {
                System.out.print(values[0].asString());
                return new VoidValue();
            }
        });
        Functions.add(new Function("print", new int[]{Types.INTEGER}, Types.VOID) {
            @Override
            public Value execute(Value[] values) {
                System.out.print(values[0].asString());
                return new VoidValue();
            }
        });
        Functions.add(new Function("print", new int[]{Types.DOUBLE}, Types.VOID) {
            @Override
            public Value execute(Value[] values) {
                System.out.print(values[0].asString());
                return new VoidValue();
            }
        });
    }

    public static boolean isExists(String key, int[] types){
        return Functions.stream().filter(o -> o.getName().equals(key) && Arrays.equals(o.getTypes(), types)).findFirst().orElse(null) != null;
    }


    public static Function get(String key, int[] types){
        if(!isExists(key, types))
            throw new RuntimeException("функции не существует " + key + " " + Arrays.toString(types));
        return Functions.stream().filter(o -> o.getName().equals(key) && Arrays.equals(o.getTypes(), types)).findFirst().orElseThrow();
    }
    public static void add(Function function){
        if(isExists(function.getName(), function.getTypes()))
            throw new RuntimeException("фунция уже создана");
        Functions.add(function);
    }


}
