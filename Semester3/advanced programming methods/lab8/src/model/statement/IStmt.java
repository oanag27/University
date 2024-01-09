package model.statement;

import model.adt.IHeap;
import model.exception.MyException;
import model.value.Value;

public interface IStmt {
    ProgramState execute(ProgramState state) throws MyException, MyException, MyException;
    IStmt deepCopy();
}
