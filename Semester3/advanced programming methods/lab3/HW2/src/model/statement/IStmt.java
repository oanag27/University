package model.statement;

import model.exception.MyException;

public interface IStmt {
    ProgramState execute(ProgramState state) throws MyException;
    IStmt deepCopy();
}
