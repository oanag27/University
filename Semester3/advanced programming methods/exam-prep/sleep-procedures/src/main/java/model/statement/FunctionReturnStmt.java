package model.statement;

import model.adt.MyIDictionary;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.type.Type;

public class FunctionReturnStmt implements IStmt{
    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        try {
            state.getAllSymTables().pop();
        } catch (EmptyADTException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new FunctionReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "return";
    }
}
