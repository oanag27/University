package model.statement;


import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStatement implements IStmt{
    Exp exp;
    IStmt repeatStmt;
    public WhileStatement(Exp e, IStmt t) {exp=e; repeatStmt =t;}
    @Override
    public String toString(){ return "(WHILE("+ exp.toString()+") " + repeatStmt.toString()+")";}
    @Override
    public ProgramState execute(ProgramState state) throws MyException { //
        Value iCondition = exp.eval(state.getSymTable(), state.getHeap());
        if (!iCondition.getType().equals(new BoolType()))
            throw new MyException("conditional expr must be boolean");
        BoolValue condition = (BoolValue) iCondition;
        if(condition.getVal()) {
            state.getExeStack().push(this);
            state.getExeStack().push(repeatStmt);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStatement(exp.deepCopy(), repeatStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            repeatStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }
}
