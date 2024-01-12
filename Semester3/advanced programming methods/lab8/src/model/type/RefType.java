package model.type;

import model.value.RefValue;
import model.value.Value;

public class RefType implements Type{
    Type inner;
    public RefType(Type inner) {this.inner=inner;}
    public Type getInner() {return inner;}
    @Override
    public RefValue defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner);
    }
    @Override
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "RefType{" +
                "inner=" + inner.toString() +
                '}';
    }
}
