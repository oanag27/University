package model.value;
import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v){val=v;}
    @Override
    public String toString() {
        return "BoolValue{" +
                "val=" + val +
                '}';
    }
    @Override
    public Type getType() {
        return new BoolType();
    }
    @Override
    public Boolean getVal() {
        return val;
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
