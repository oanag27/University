package model.expression;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.Value;
import model.value.StringValue;

public class ValueExp implements Exp{
    Value e;
    private Value value;
    public ValueExp(Value ee){
        e=ee;
        value = e;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        e.getType();
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public String toString() {
        return "ValueExp{" +
                "e=" + e.toString() +
                '}';
    }
}
