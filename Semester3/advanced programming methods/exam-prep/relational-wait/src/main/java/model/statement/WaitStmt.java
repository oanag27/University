package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class WaitStmt implements IStmt{
    private final int value;

    public WaitStmt(int value) {
        this.value = value;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (value != 0) {
            MyIStack<IStmt> exeStack = state.getExeStack();
            exeStack.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(value))),
                    new WaitStmt(value - 1)));
            state.setExeStack(exeStack);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(value);
    }

    @Override
    public String toString() {
        return "WaitStmt{" +
                "value='" + value + '\'' +
                '}';
    }
}
