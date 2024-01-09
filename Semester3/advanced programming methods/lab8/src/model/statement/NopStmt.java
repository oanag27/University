package model.statement;

import model.exception.MyException;

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
    public String toString() {
        return "No operations";
    }
}
