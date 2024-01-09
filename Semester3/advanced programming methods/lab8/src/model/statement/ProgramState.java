package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.value.Value;

import java.io.BufferedReader;
import java.util.logging.MemoryHandler;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<Value, BufferedReader> fileTable; //FileTable implementation
    IStmt originalProgram;                          //optional field, but good to have
    IHeap<Integer, Value> heap;

    public ProgramState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl,
                    MyIList<Value> ot, IStmt prg,MyIDictionary<Value, BufferedReader> fileTabl,IHeap<Integer, Value> heap){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg.deepCopy();         //recreate the entire original program
        fileTable = fileTabl;
        this.heap=heap;
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
                "exeStack=" + exeStack +'\n'+
                ", symTable=" + symTable +'\n'+
                ", out=" + out +'\n'+
                ", originalProgram=" + originalProgram +'\n'+
                ", File Table"+ fileTable.getKeys() +'\n'+
                heap+'\n'+
                '}';
    }

    public MyIDictionary<Value, BufferedReader> getFileTable() { return fileTable; }

    public void setFileTable(MyIDictionary<Value, BufferedReader> fileTabl) {
        fileTable = fileTabl;
    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

//    public void setHeap(IHeap<Integer, Value> newHeap) {
//        this.heap = newHeap;
//    }

}
