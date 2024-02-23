package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.expression.Exp;
import model.expression.RelationalExp;
import model.type.Type;

public class Switch implements IStmt {
    Exp exp1;
    Exp exp2;
    Exp exp3;
    IStmt stmt1;
    IStmt stmt2;
    IStmt stmt3;
    public Switch(Exp e1, Exp e2, Exp e3, IStmt s1, IStmt s2, IStmt s3) {
        exp1 = e1;
        exp2 = e2;
        exp3 = e3;
        stmt1 = s1;
        stmt2 = s2;
        stmt3 = s3;
    }
    @Override
    public String toString() {
        return "Switch{}";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt newStmt =
                new IfStmt(
                        new RelationalExp(exp1, exp2,"=="),
                        stmt1,
                        new IfStmt(
                                new RelationalExp( exp1, exp3,"=="),
                                stmt2,
                                stmt3));
        exeStack.push(newStmt);
        state.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new Switch(exp1, exp2, exp3, stmt1, stmt2, stmt3);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp1 = exp1.typecheck(typeEnv);
        Type typexp2 = exp2.typecheck(typeEnv);
        Type typexp3 = exp3.typecheck(typeEnv);

        if (typexp1.equals(typexp2) && typexp3.equals(typexp2)) {
            stmt1.typecheck(typeEnv.deepCopy());
            stmt2.typecheck(typeEnv.deepCopy());
            stmt3.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The Expressions of Switch Statement were not of the same type!");

    }
}
