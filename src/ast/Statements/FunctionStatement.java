package ast.Statements;

import ast.Expressions.Expression;
import lib.Function;
import lib.Functions;
import lib.Types;
import lib.Values.Value;
import lib.Values.VoidValue;
import lib.Variables;

public class FunctionStatement implements Statement{
    private int ReturnType;
    private Statement statement;
    private String name;
    private int[] argsType;

    public FunctionStatement(int returnType, Statement statement, String name,int[] argsType) {
        ReturnType = returnType;
        this.statement = statement;
        this.name = name;
        this.argsType = argsType;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void execute() {
        Function function = new Function(name, argsType, ReturnType) {
            @Override
            public Value execute(Value[] values) {
                Variables.AddStack();
                BlockStatement s = (BlockStatement)statement;

                for (int i = 0; i < argsType.length; i++){
                    AssignmentValueStatement statement = ((AssignmentValueStatement)((BlockStatement)s.statements.get(i)).statements.get(1));
                    statement.setValue(values[values.length - i - 1]);
                }


                try {
                    statement.execute();
                    if (ReturnType == Types.VOID) {
                        Variables.RemoveTopLevel();
                        return new VoidValue();
                    }
                    throw new RuntimeException("функция описывает возвращение значения типа " + Types.names.get(ReturnType) + " но не возвращает ничего");
                }
                catch (ReturnStatement rt){
                    if(ReturnType == rt.getValue().getType()) {
                        Variables.RemoveTopLevel();
                        return rt.getValue();
                    }
                    throw new RuntimeException("возвращаемый тип не соответветсувет обьявленому");
                }

            }
        };
        Functions.add(function);
    }
}
