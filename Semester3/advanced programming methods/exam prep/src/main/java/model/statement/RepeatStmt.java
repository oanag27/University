package model.statement;

import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.exception.MyException;
import model.expression.Exp;
import model.expression.NotExp;
import model.type.BoolType;
import model.type.Type;

public class RepeatStmt implements IStmt{
    IStmt stmt;
    Exp exp;
    public RepeatStmt(IStmt stmt, Exp exp)
    {
        this.stmt=stmt;
        this.exp=exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        IStmt whileStatement = new CompStmt(stmt, new WhileStatement(new NotExp(exp), stmt));
        exeStack.push(whileStatement);
        return null;
//        IStmt newStmt = new CompStmt(stmt, new WhileStatement(new NotExp(exp), stmt));
//        state.getExeStack().push(newStmt);
//        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new RepeatStmt(stmt.deepCopy(),exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expressionType = exp.typecheck(typeEnv);
        stmt.typecheck(typeEnv);
        if (expressionType.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new MyException("The Expression of Repeat Statement was not Boolean");
//        Type typExp = exp.typecheck(typeEnv); // Check the type of the expression
//        if(typExp.equals(new BoolType())) {
//            stmt.typecheck(typeEnv.deepCopy());
//            return typeEnv;
//        }
//        else
//            throw new MyException("Repeat statement: the condition of WHILE has not the type bool");
    }
    @Override
    public String toString() {
        return "repeat{ " + stmt + " } until " + exp;
    }
}
