package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;
import model.value.Value;

public interface IStmt {
    ProgramState execute(ProgramState state) throws MyException, MyException, MyException;
    IStmt deepCopy();

    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
