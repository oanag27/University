package model.value;

import model.type.Type;

public interface Value {
    Type getType();

    Value deepCopy();

    Object getVal();

    boolean equals(Object other);
}
