package model.statement;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
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

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
