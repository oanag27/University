package model.statement;
import model.adt.IHeap;
import model.adt.MyIStack;
import model.exception.MyException;
import model.value.Value;

public class CompStmt implements IStmt {   //compound statement is top of exeStack
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt stmt1, IStmt stmt2) { first = stmt1; snd = stmt2;}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + first.toString() +
                ";" + snd.toString() +
                ')';
    }
}
