package model.expression;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.Value;

public class ValueExp implements Exp{
    Value e;
    public ValueExp(Value ee){
        e=ee;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e +
                '}';
    }
}
