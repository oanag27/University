package model.statement;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.Type;
import model.adt.MyIStack;
import model.value.Value;

public class AssignStmt implements IStmt { //assignment is on top of the stack
    String id;
    Exp exp;
    public AssignStmt(String identif, Exp expression) {id = identif; exp = expression;}
    @Override
    public String toString() {
        return id + '=' + exp.toString();
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl);
            Type typId = (symTbl.lookUp(id)).getType();
            if ((val.getType()).equals(typId))
                symTbl.update(id, val);
            else
               throw new MyException("declared type"+ id + "and type of the assigned expr do not match");
       } else throw new MyException("used variable" + id + "was not declared before");
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }
}
