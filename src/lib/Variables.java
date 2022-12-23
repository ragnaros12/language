package lib;

import Exceptions.TypeException;
import Exceptions.VariableAlreadyDefinedException;
import Exceptions.VariableNotDefinedException;
import ast.Statements.Statement;
import lib.Values.StringValue;
import lib.Values.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Variables {

    private static final ArrayList<Variable> global;
    private static final Stack<ArrayList<Variable>> allVars;

    public static void AddStack(){
        allVars.push(new ArrayList<>());
    }
    public static void RemoveTopLevel(){
        allVars.pop();
    }

    static  {
        global = new ArrayList<>();
        allVars = new Stack<>();
    }

    public static boolean isExists(String key){
        return
                allVars.peek().stream().filter(o -> o.getName().equals(key)).findFirst().orElse(null) != null ||
                global.stream().filter(o -> o.getName().equals(key)).findFirst().orElse(null) != null;
    }

    public static Value get(String key){
        if(!isExists(key))
            return null;
        return global.stream().filter(o -> o.getName().equals(key)).findFirst().orElse(
                allVars.peek().stream().filter(o -> o.getName().equals(key)).findFirst().orElseThrow()
        ).getValue();
    }
    public static void add(String key, Value value){
        if(isExists(key))
            throw new VariableAlreadyDefinedException();
        ArrayList<Variable> variables = allVars.size() == 0 ? global : allVars.peek();
        variables.add(new Variable(key, value, value.getType()));
    }
    public static void put(String key, Value value){
        if(!isExists(key))
            throw new VariableNotDefinedException();
        Variable res = global.stream().filter(o -> o.getName().equals(key)).findFirst().orElse(
                allVars.peek().stream().filter(o -> o.getName().equals(key)).findFirst().orElseThrow()
        );
        if(res.getType() != value.getType() && res.getType() != Types.STRING)
            throw new TypeException();
        else if(res.getType() != value.getType() && res.getType() == Types.STRING)
            res.setValue(new StringValue(value.asString()));
        res.setValue(value);
    }

}
