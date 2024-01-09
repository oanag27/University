package model.statement;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;
import model.value.Value;

public class VarDeclStmt implements IStmt {    //variable declaration statement on top of the stack
    String name;
    Type type;
    public VarDeclStmt(String n, Type t) { name = n; type = t; }
    @Override
    public String toString() {
        return name + "=(" + type.toString()+")";
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!symTbl.isDefined(name)) { //var not already defined in the symbol table
            Value defaultValue = type.defaultValue(); //retrieves the default value
            symTbl.push(name, defaultValue);
        } else throw new MyException("variable with this name already found"+name); //already defined in the symbol table
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }
}
