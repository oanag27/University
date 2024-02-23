package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;
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
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        return tbl.lookUp(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id); //string
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookUp(id);
    }

//    @Override
//    public Value eval(MyIDictionary<String, Value> symTable, IHeap<Integer, Value> heap) {
//        return (Value) symTable.lookup(id);
//    }
}
