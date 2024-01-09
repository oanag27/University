package model.statement;

import model.adt.*;
import model.exception.MyException;
import model.value.Value;

public class ForkStatement implements IStmt {
    private final IStmt statement;

    public ForkStatement(IStmt state) {
        this.statement = state;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> newExeStack = new MyStack<>();
        newExeStack.push(statement);
        MyIDictionary<String, Value> newSymTable;
        newSymTable = state.getSymTable().deepCopy();
        ProgramState newProgramState = new ProgramState(newExeStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
        newProgramState.setCurrentID();
        return newProgramState;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStatement(this.statement);
    }

    @Override
    public String toString() {
        return "ForkStatement{" +
                "state=" + statement.toString() +
                '}';
    }
}
