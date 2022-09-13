package lib;

import Exceptions.TypeException;
import Exceptions.VariableAlreadyDefinedException;
import Exceptions.VariableNotDefinedException;
import ast.Statements.Statement;
import lib.Values.StringValue;
import lib.Values.Value;

import java.util.ArrayList;

public class Variables {
    private static int StackSize = 0 ;
    public static ArrayList<Variable> Variables;

    public static void AddStack(){
        StackSize++;
    }
    public static int GetStack(){
        return StackSize;
    }
    public static void RemoveTopLevel(){
        Variables.removeIf(variable -> variable.getStackPos() == GetStack());
        StackSize = GetStack() - 1;
    }

    static  {
        Variables = new ArrayList<>();
    }

    public static boolean isExists(String key){
        return Variables.stream().filter(o -> o.getName().equals(key)).findFirst().orElse(null) != null;
    }


    public static Value get(String key){
        if(!isExists(key))
            return null;
        return Variables.stream().filter(o -> o.getName().equals(key)).findFirst().orElseThrow().getValue();
    }
    public static void add(String key, Value value){
        if(isExists(key))
            throw new VariableAlreadyDefinedException();
        Variables.add(new Variable(key, value, value.getType(), StackSize));
    }
    public static void put(String key, Value value){
        if(!isExists(key))
            throw new VariableNotDefinedException();
        Variable res = Variables.stream().filter(o -> o.getName().equals(key)).findFirst().orElseThrow();
        if(res.getType() != value.getType() && res.getType() != Types.STRING)
            throw new TypeException();
        else if(res.getType() != value.getType() && res.getType() == Types.STRING)
            res.setValue(new StringValue(value.asString()));
        res.setValue(value);
    }

}
