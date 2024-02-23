package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value {
    String val;
    private Type type;

    public StringValue(String v) {
        val = v;
        type = new StringType();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getVal() {
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

    @Override
    public boolean equals(Object other) {
        return other instanceof StringValue;
    }

}
