package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;
import model.value.Value;

public class ValueExp implements Exp{
    Value e;
    private Value value;
    public ValueExp(Value ee){
        e=ee;
        value = e;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        e.getType();
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

//    @Override
//    public Value eval(MyIDictionary<String, Value> symTable, IHeap<Integer, Value> heap) {
//        return value;
//    }

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e.toString() +
                '}';
    }
}
