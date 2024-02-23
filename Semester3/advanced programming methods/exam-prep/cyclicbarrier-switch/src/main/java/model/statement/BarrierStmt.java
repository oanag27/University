package model.statement;

import javafx.util.Pair;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyList;
import model.exception.MyException;
import model.expression.Exp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;
import java.util.Vector;

public class BarrierStmt implements IStmt {
    public String variableName;
    public Exp expression;

    public BarrierStmt(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
    public String getVariableName() {
        return variableName;
    }
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    public Exp getExpression() {
        return expression;
    }
    public void setExpression(Exp expression) {
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        try {
            Value expressionEval = expression.eval(state.getSymTable(), state.getHeap());
            if (!expressionEval.getType().equals(new IntType()))
                throw new MyException(String.format("Expression %s should evaluate to an integer type!", expression.toString()));

            int expressionValue = ((IntValue) expressionEval).getVal();
            int newLocation = state.getBarrierTable().put(new Pair<>(expressionValue, new MyList<>()));

            Value variableValue = state.getSymTable().lookUp(variableName);
            if (variableValue == null)
                throw new MyException(String.format("Variable %s has not been declared!", variableName));
            if (!variableValue.getType().equals(new IntType()))
                throw new MyException(String.format("Variable %s should be of integer type!", variableName));

            state.getSymTable().update(variableName, new IntValue(newLocation));

        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }


    @Override
    public IStmt deepCopy() {
        return new BarrierStmt(variableName, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            Type expressionType = expression.typecheck(typeEnv);
            if (!expressionType.equals(new IntType()))
                throw new MyException(String.format("Expression %s should evaluate to an integer type!", expression.toString()));

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
        return "BarrierStmt{" +
                "variableName='" + variableName + '\'' +
                ", expression=" + expression +
                '}';
    }
}
