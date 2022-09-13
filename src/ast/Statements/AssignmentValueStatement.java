package ast.Statements;

import lib.Values.Value;
import lib.Variables;

public class AssignmentValueStatement implements Statement{
    private Value value;
    private final String name;

    public AssignmentValueStatement(String name, Value value) {
        this.value = value;
        this.name = name;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public void execute() {
        Variables.put(name, value);
    }
}
