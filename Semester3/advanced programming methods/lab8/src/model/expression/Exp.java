package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.Type;
import model.value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException;
    Exp deepCopy();

    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;

   // Value eval(MyIDictionary<String, Value> symTable, IHeap<Integer, Value> heap) throws MyException;
}
