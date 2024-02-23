package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int addr, Type location) {
        address = addr;
        locationType = location;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Integer getVal() {
        return address;
    }

    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        RefValue refValue = (RefValue) other;
        return address == refValue.address && locationType.equals(refValue.locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

}
