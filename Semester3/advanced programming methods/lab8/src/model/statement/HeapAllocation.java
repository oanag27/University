package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class HeapAllocation implements IStmt{
    String varName;
    Exp exp;
    public HeapAllocation(String varName,Exp exp)
    {
        this.varName=varName;
        this.exp=exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String,Value> symTable= state.getSymTable();
        Value value= symTable.lookup(varName);
        if(varName ==null ||!(value.getType() instanceof RefType))
            throw new MyException("Variable not of reference type");
        Type locationType= ((RefType)value.getType()).getInner();
        Value expValue= exp.eval(symTable,state.getHeap());
        if(!expValue.getType().equals(locationType))
            throw new MyException("Type of expression and type of variable do not match");
        int newAddr = state.getHeap().getFree();
        state.getHeap().write(newAddr,expValue);
        RefValue newRefValue=new RefValue(newAddr,locationType);
        symTable.update(varName,newRefValue);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocation(varName,exp.deepCopy());
    }

    @Override
    public String toString() {
        return "HeapAllocation{" +
                "varName='" + varName + '\'' +
                ", exp=" + exp +
                '}';
    }
}
