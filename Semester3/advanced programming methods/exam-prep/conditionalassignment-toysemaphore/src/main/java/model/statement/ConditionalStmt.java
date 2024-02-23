package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.expression.Exp;
import model.expression.VarExp;
import model.type.BoolType;
import model.type.Type;


public class ConditionalStmt implements IStmt{
    private final String var;
    private final Exp exp1;
    private final Exp exp2;
    private final Exp exp3;
    public ConditionalStmt(String v, Exp e1, Exp e2, Exp e3){
        var = v;
        exp1 = e1;
        exp2 = e2;
        exp3 = e3;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt converted = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        exeStack.push(converted);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type variableType = new VarExp(var).typecheck(typeEnv);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        Type type3 = exp3.typecheck(typeEnv);
        if (type1.equals(new BoolType()) && type2.equals(variableType) && type3.equals(variableType))
            return typeEnv;
        else
            throw new MyException("The conditional assignment is invalid!");
    }

    @Override
    public String toString() {
        return "ConditionalStmt{" +
                "var='" + var + '\'' +
                ", exp1=" + exp1 +
                ", exp2=" + exp2 +
                ", exp3=" + exp3 +
                '}';
    }
}
