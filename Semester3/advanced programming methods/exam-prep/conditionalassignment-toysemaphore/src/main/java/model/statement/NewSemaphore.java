package model.statement;

import javafx.util.Pair;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class NewSemaphore implements IStmt{
    private String variableName;
    private Exp exp1;
    private Exp exp2;

    public NewSemaphore(String variableName, Exp exp1, Exp exp2) {
        this.variableName = variableName;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        try {
            Value expr1Value = exp1.eval(state.getSymTable(), state.getHeap());
            Value expr2Value = exp2.eval(state.getSymTable(), state.getHeap());

            if (!expr1Value.getType().equals(new IntType()))
                throw new MyException(String.format("Expression '%s' should evaluate to an integer!", exp1.toString()));
            if (!expr2Value.getType().equals(new IntType()))
                throw new MyException(String.format("Expression '%s' should evaluate to an integer!", exp2.toString()));

            int value1 = ((IntValue) expr1Value).getVal();
            int value2 = ((IntValue) expr2Value).getVal();

            int newLocation = state.getSemaphoreTable().getFirstFreeLocation();  //first free location in the semaphore table
            state.getSemaphoreTable().put(newLocation, new Pair<>(value1, new Pair<>(new Vector<>(), value2))); //put the new semaphore in the semaphore table
            ProgramState.semaphoreLock.lock();//lock the semaphore table =>Ensure thread safety

            Value variableValue = state.getSymTable().lookUp(variableName);
            if (variableValue == null) {
                ProgramState.semaphoreLock.unlock();
                throw new MyException(String.format("Variable '%s' has not been declared!", variableName));
            }
            if (!variableValue.getType().equals(new IntType())) {
                ProgramState.semaphoreLock.unlock();
                throw new MyException(String.format("Variable '%s' should have integer type!", variableName));
            }
            state.getSymTable().put(variableName, new IntValue(newLocation));
            ProgramState.semaphoreLock.unlock(); //release the lock
            return null;
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphore(variableName, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            Type varType = typeEnv.lookUp(variableName);
            if (varType == null)
                throw new MyException(String.format("Variable '%s' has not been declared!", variableName));
            if (!varType.equals(new IntType()))
                throw new MyException(String.format("Variable '%s' should have integer type!", variableName));
            if (!exp1.typecheck(typeEnv).equals(new IntType()))
                throw new MyException(String.format("Expression '%s' should evaluate to an integer!", exp1.toString()));
            if (!exp2.typecheck(typeEnv).equals(new IntType()))
                throw new MyException(String.format("Expression '%s' should evaluate to an integer!", exp2.toString()));
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NewSemaphore{" +
                "variableName='" + variableName + '\'' +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                '}';
    }
}
