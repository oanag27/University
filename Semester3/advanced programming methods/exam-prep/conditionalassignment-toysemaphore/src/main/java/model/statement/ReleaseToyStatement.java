package model.statement;

import javafx.util.Pair;
import model.adt.IToySemaphore;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public class ReleaseToyStatement implements IStmt{
    private final String var;

    public ReleaseToyStatement(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        try {
            Value variableValue = state.getSymTable().lookUp(var);
            if (variableValue == null)
                throw new MyException(String.format("Variable '%s' has not been declared!", var));
            if (!variableValue.getType().equals(new IntType()))
                throw new MyException(String.format("Variable '%s' should have integer type!", var));

            int semaphoreLocation = ((IntValue) variableValue).getVal();
            ProgramState.semaphoreLock.lock();  //acquire a lock the semaphore table =>Ensure thread safety
            Pair<Integer, Pair<List<Integer>, Integer>> semaphoreEntry = state.getSemaphoreTable().lookUp(semaphoreLocation);
            if (semaphoreEntry == null) {
                ProgramState.semaphoreLock.unlock();
                throw new MyException("Invalid semaphore location!");
            }
            //extract the list of acquired programs IDs and the maximum allowed semaphore value from the semaphore entry
            Pair<List<Integer>, Integer> acquiredPrograms = semaphoreEntry.getValue();
            Integer programId = state.getId();
            if (acquiredPrograms.getKey().contains(programId))
                acquiredPrograms.getKey().remove(acquiredPrograms.getKey().indexOf(programId));
            ProgramState.semaphoreLock.unlock();
            return null;
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseToyStatement(this.var);
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
        return "ToyRelease (" + this.var + ')';
    }
}
