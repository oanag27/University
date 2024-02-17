package model.statement;

import model.adt.*;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<Value, BufferedReader> fileTable; //FileTable implementation
    IStmt originalProgram;                          //optional field, but good to have
    IHeap<Integer, Value> heap;
    private int id;
    private static int currentID = 1;

    public ProgramState(MyIStack<IStmt> newExeStack, MyIDictionary<String, Value> newSymTable, MyIList<Value> out, MyIDictionary<Value, BufferedReader> fileTable, IHeap<Integer, Value> heap) {
        // for fork
        this.exeStack = newExeStack;
        this.symTable = newSymTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
    }

    public synchronized void setCurrentID(){
        currentID++;
        this.id = currentID;
    }

    public ProgramState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl,
                    MyIList<Value> ot, IStmt prg ,MyIDictionary<Value, BufferedReader> fileTabl,IHeap<Integer, Value> heap){
        //for main
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = prg;         //recreate the entire original program
//        this.originalProgram = prg;         //recreate the entire original program
        this.fileTable = fileTabl;
        this.heap=heap;
        id=1;
        stk.push(this.originalProgram);
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }
    public ProgramState oneStep() throws MyException, EmptyADTException{
        if (exeStack.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
    public void typeCheck() throws MyException{
        originalProgram.typecheck(new MyDictionary<>());
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
                "id=" + this.id + '\n' +
                "exeStack=" + exeStack +'\n'+
                ", symTable=" + symTable +'\n'+
                ", out=" + out +'\n'+
                //", originalProgram=" + originalProgram +'\n'+
                ", File Table"+ fileTable.getKeys() +'\n'+
                heap+'\n'+
                '}';
    }

    public int getId(){
        return id;
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
