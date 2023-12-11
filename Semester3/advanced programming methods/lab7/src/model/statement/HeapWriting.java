package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

import java.util.Objects;

public class HeapWriting implements IStmt {
    private String varName;
    private Exp expression;

    public HeapWriting(String varName, Exp exp) {
        this.varName = varName;
        this.expression = exp;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable is not defined in SymTable");
        }

        Value varVal = symTable.lookup(varName);
        if (!(varVal.getType() instanceof RefType)) {
            throw new MyException("Variable is not of RefType");
        }

        int addr = ((RefValue) varVal).getVal();
        if (!state.getHeap().isDefined(addr)) {
            throw new MyException("Invalid address in Heap");
        }

        Value exprValue = expression.eval(symTable, state.getHeap());
        Type locationType = ((RefType) varVal.getType()).getInner();

        if (!exprValue.getType().equals(locationType)) {
            throw new MyException("Type is not ok in heap ");
        }

        state.getHeap().write(addr, exprValue);
        return state;
    }

    @Override
    public String toString() {
        return "HeapWriting{" +
                "varName='" + varName + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriting(varName, expression);
    }

}
