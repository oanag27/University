package model.statement;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class NewStatement implements IStmt{
    private String varName;
    private Exp expression;
    String id;

    public NewStatement(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = (Type) typeEnv.lookup(varName); // Lookup the type of the variable in the symbol table
        Type typexp = expression.typecheck(typeEnv); // Check the type of the expression

        // Check if the variable type is a reference type and matches the type of the expression
        if (typevar.equals(new RefType(typexp))) {
            return typeEnv;
        } else {
            throw new MyException("NEW statement: right hand side and left hand side have different types");
        }
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {  //todo:implementation
        if (!state.getSymTable().isDefined(varName))
            throw new MyException("Variable not defined");

        Value varValue = (Value) state.getSymTable().lookup(varName);

        if (!(varValue.getType() instanceof RefType))
            throw new MyException("Variable is not a reference type");

        Value val = expression.eval(state.getSymTable(), state.getHeap());
        RefType varRefType = (RefType) varValue.getType();

        if (!varRefType.getInner().equals(val.getType()))
            throw new MyException("Variable type and expression type do not match (new)");

        int address = state.getHeap().getFree();
        state.getHeap().add(address, val);
        state.getSymTable().push(varName, (Value) new RefValue(address, val.getType()));

        return null;
    }
    @Override
    public String toString() {
        return "new("+ varName+","+expression+")";
    }
}
