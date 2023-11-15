package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value{

    String val;
    private Type type;
    public StringValue(String v){
        val = v;
        type = new StringType();
    }
    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Object getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "val='" + val;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
