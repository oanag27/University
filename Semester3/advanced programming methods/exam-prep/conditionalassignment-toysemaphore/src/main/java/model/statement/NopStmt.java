package model.statement;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;

public class NopStmt implements IStmt {
    public NopStmt(){}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "No operations";
    }
}
