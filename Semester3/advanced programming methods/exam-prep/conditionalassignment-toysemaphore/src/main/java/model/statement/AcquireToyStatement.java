package model.statement;

import javafx.util.Pair;
import model.adt.IToySemaphore;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public class AcquireToyStatement implements IStmt{

    private final String var;

    public AcquireToyStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        try {
            Value variableValue = state.getSymTable().lookUp(var);
            if (variableValue == null) //var not declared
                throw new MyException(String.format("Variable '%s' has not been declared!", var));
            if (!variableValue.getType().equals(new IntType()))
                throw new MyException(String.format("Variable '%s' should have integer type!", var));

            int semaphoreLocation = ((IntValue) variableValue).getVal();
            ProgramState.semaphoreLock.lock(); //acquire a lock the semaphore table =>Ensure thread safety
            Pair<Integer, Pair<List<Integer>, Integer>> semaphoreEntry = state.getSemaphoreTable().lookUp(semaphoreLocation);//get the semaphore from the semaphore table
            if (semaphoreEntry == null) {
                ProgramState.semaphoreLock.unlock();
                throw new MyException("Invalid semaphore location!");
            }
            Integer semaphore = semaphoreEntry.getKey();  //extract the semaphore value
            List<Integer> semaphorelist = semaphoreEntry.getValue().getKey(); //extract the list of threads
            Integer semaphore2 = semaphoreEntry.getValue().getValue(); //extract the max allowed semaphore value
            if (semaphore-semaphore2 > semaphorelist.size()) {
                if (!semaphorelist.contains(state.getId()))
                    semaphorelist.add(state.getId());
            }
            else
                state.getExeStack().push(new AcquireToyStatement(var));
            ProgramState.semaphoreLock.unlock();
            return null;
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireToyStatement(this.var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> table) throws MyException {
        try {
            Type var_type = table.lookUp(var);
            if (var_type == null)
                throw new MyException(String.format("Variable '%s' has not been declared!", var));
            if (!var_type.equals(new IntType()))
                throw new MyException(String.format("Variable '%s' should have integer type!", var));
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return table;
    }

    @Override
    public String toString() {
        return "ToyAcquire(" + this.var + ")";
    }
}
