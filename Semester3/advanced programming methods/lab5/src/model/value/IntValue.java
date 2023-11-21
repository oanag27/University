package model.value;

import model.type.IntType;
import model.type.Type;

public class IntValue implements Value{
    int val;
    public IntValue(int v){val=v;}
    @Override
    public String toString() {
        return "IntValue{" +
                "val=" + val +
                '}';
    }
    @Override
    public Type getType() {
        return new IntType();
    }
    @Override
    public Integer getVal() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
