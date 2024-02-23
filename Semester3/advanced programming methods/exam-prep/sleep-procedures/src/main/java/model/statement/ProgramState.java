package model.statement;

import model.adt.*;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIStack<MyIDictionary <String, Value>> symTables; //SymTableStack implementation
    MyIList<Value> out;
    IProcedureTable procedureTable;
    MyIDictionary<Value, BufferedReader> fileTable; //FileTable implementation
    IStmt originalProgram;                          //optional field, but good to have
    IHeap<Integer, Value> heap;
    private int id;
    private static int currentID = 1;

    public ProgramState(MyIStack<IStmt> newExeStack, MyIDictionary<String, Value> newSymTable, MyIList<Value> out, MyIDictionary<Value, BufferedReader> fileTable, IHeap<Integer, Value> heap,IProcedureTable procedureTable) {
        // for fork
        this.exeStack = newExeStack;
        this.symTable = newSymTable;
        this.symTables = new MySymtableStack();
        this.symTables.push(newSymTable);
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procedureTable = procedureTable;
    }

    public synchronized void setCurrentID(){
        currentID++;
        this.id = currentID;
    }

    public ProgramState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl,
                    MyIList<Value> ot, IStmt prg ,MyIDictionary<Value, BufferedReader> fileTabl,IHeap<Integer, Value> heap,IProcedureTable procedureTable){
        //for main
        this.exeStack = stk;
        this.symTable = symtbl;
        this.symTables = new MySymtableStack();
        this.symTables.push(symtbl);
        this.out = ot;
        this.originalProgram = prg;         //recreate the entire original program
//        this.originalProgram = prg;         //recreate the entire original program
        this.fileTable = fileTabl;
        this.heap=heap;
        id=1;
        this.procedureTable = procedureTable;
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

//    public MyIDictionary<String, Value> getSymTable(){
//        return symTable;
//    }

    public MyIStack<IStmt> getExeStack(){
        return exeStack;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

//    public void setSymTable(MyIDictionary<String, Value> symTable) {
//        this.symTable = symTable;
//    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }
    public void setProcedureTable(IProcedureTable procedureTable) {
        this.procedureTable = procedureTable;
    }
    public IProcedureTable getProcedureTable() {
        return procedureTable;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTables.peek();
    }
    public MyIStack<MyIDictionary<String, Value>> getAllSymTables() {
        return symTables;
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
    public String symTablesToString() throws MyException, EmptyADTException {
        StringBuilder returnString = new StringBuilder();
        if (symTables.isEmpty())
            return returnString.toString() + '\n';

        MyStack<MyIDictionary<String, Value>> stackCopy = new MyStack<>();
        while (!symTables.isEmpty()) {
            if (symTables.peek() instanceof IStmt)
                returnString.append((symTables.peek()).toString()).append('\n');
            else
                returnString.append(symTables.peek().toString()).append('\n');
            stackCopy.push(symTables.pop());
        }

        while (!stackCopy.isEmpty()) {
            symTables.push(stackCopy.pop());
        }

        return returnString.toString();
    }
    public String procedureTableToString() throws MyException {
        StringBuilder procedureTableStringBuilder = new StringBuilder();
        for (String key: procedureTable.getKeys()) {
            procedureTableStringBuilder.append(String.format("%s - %s: %s\n", key, procedureTable.lookUp(key).getKey(), procedureTable.lookUp(key).getValue()));
        }
        procedureTableStringBuilder.append("\n");
        return procedureTableStringBuilder.toString();
    }


//    public void setHeap(IHeap<Integer, Value> newHeap) {
//        this.heap = newHeap;
//    }

}
