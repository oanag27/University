package model.statement;

import javafx.util.Pair;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;

public class AwaitStmt implements IStmt{
    private String variableName;

    public AwaitStmt(String variableName) {
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        try {
            Value variableValue = state.getSymTable().lookUp(variableName);
            if (variableValue == null)
                throw new MyException(String.format("Variable %s has not been declared!", variableName));
            if (!variableValue.getType().equals(new IntType()))
                throw new MyException(String.format("Variable %s should be of integer type!", variableName));

            int variableInt = ((IntValue) variableValue).getVal();
            Pair<Integer, MyIList<Integer>> barrierTableEntry = state.getBarrierTable().lookUp(variableInt);
            if (barrierTableEntry == null)
                throw new MyException("Invalid barrier table location!");

            int barrierTableValue = barrierTableEntry.getKey();
            List<Integer> barrierPrograms = (List<Integer>) barrierTableEntry.getValue();

            if (barrierTableValue > barrierPrograms.size()) {
                if (!barrierPrograms.contains(state.getId()))
                    barrierPrograms.add(state.getId());
                state.getExeStack().push(this);
            }
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(variableName);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            Type variableType = typeEnv.lookUp(variableName);
            if (variableType == null)
                throw new MyException(String.format("Variable %s has not been declared!", variableName));
            if (!variableType.equals(new IntType()))
                throw new MyException(String.format("Variable %s should be of integer type!", variableName));

        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "AwaitStmt{" +
                "variableName='" + variableName + '\'' +
                '}';
    }
}
