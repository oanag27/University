package model.statement;

import model.exception.MyException;
import model.expression.Exp;
import model.value.Value;

public class PrintStmt implements IStmt {
    Exp exp;
    public PrintStmt(Exp expression) { exp = expression; }
    @Override
    public String toString(){ return "print(" +exp.toString()+")";}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value print = exp.eval(state.getSymTable(), state.getHeap());  //evaluate the expression
        state.getOut().push(print);  //print val added to the output stream
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
