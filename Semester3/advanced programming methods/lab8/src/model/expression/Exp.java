package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException;
    Exp deepCopy();

   // Value eval(MyIDictionary<String, Value> symTable, IHeap<Integer, Value> heap) throws MyException;
}
