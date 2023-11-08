package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.value.Value;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram; //optional field, but good to have

    public ProgramState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl,
                    MyIList<Value> ot, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg.deepCopy();//recreate the entire original prg
        stk.push(prg);
    }

    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public MyIStack<IStmt> getExeStack(){
        return exeStack;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "exeStack=" + exeStack +
                ", symTable=" + symTable +
                ", out=" + out +
                ", originalProgram=" + originalProgram +
                '}';
    }
}
