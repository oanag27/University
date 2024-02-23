package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.type.Type;

public class SleepStmt implements IStmt{
    private int number;
    public SleepStmt(int number){
        this.number = number;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        if (number > 0) {
            exeStack.push(new SleepStmt(number - 1));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "SleepStmt{" +
                "number=" + number +
                '}';
    }
}
