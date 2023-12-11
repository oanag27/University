package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.value.RefValue;
import model.value.Value;

public class HeapReading implements Exp {
    private Exp expression;
    IHeap<Integer,Value> heap;

    public HeapReading(Exp exp,IHeap<Integer,Value> heap) {
        this.expression = exp;
        this.heap=heap;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value val = expression.eval(tbl, heap);
        if (!(val instanceof RefValue)) {
            throw new MyException("Expression is not reference value");
        }
        RefValue ref = (RefValue) val;
        if (!heap.isDefined(ref.getVal())) {
            throw new MyException("Address is not defined in the heap");
        }
        return heap.lookup(ref.getVal());
    }

    @Override
    public Exp deepCopy() {
        return new HeapReading(expression,heap);
    }

    @Override
    public String toString() {
        return "HeapReading{" +
                "expression=" + expression +
                '}';
    }
}
