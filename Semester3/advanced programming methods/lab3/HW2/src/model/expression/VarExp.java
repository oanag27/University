package model.expression;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.Value;

public class VarExp implements Exp{
    String id;
    public VarExp(String id1){
        id= id1;
    }
    @Override
    public String toString() {
        return "VarExp{" +
                "id='" + id + '\'' +
                '}';
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return tbl.lookUp(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id); //string
    }
}
