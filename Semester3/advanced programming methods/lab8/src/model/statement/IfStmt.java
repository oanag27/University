package model.statement;

import model.adt.IHeap;
import model.exception.MyException;
import model.expression.Exp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}
    @Override
    public String toString(){ return "(IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+"))";}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value ifcondition = exp.eval(state.getSymTable(), state.getHeap()); //exp eval prg state symbol table
        if (!ifcondition.getType().equals(new BoolType()))   //check boolean
            throw new MyException("Conditional expression must be boolean");
        BoolValue condition = (BoolValue) ifcondition;
        if(condition.getVal())
            state.getExeStack().push(thenS);
        else  state.getExeStack().push(elseS);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(),thenS.deepCopy(), elseS.deepCopy());
    }
}
