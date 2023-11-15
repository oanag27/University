package model.expression;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl) throws MyException;
    Exp deepCopy();
}
