package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type{
    @Override
    public StringValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }
    @Override
    public String toString() {
        return "String";
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
