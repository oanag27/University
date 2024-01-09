package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class HeapReading implements Exp {
    private Exp expression;
    IHeap<Integer,Value> heap;

//    public HeapReading(Exp exp,IHeap<Integer,Value> heap) {
//        this.expression = exp;
//        this.heap=heap;
//    }
    public HeapReading(Exp exp) {
        this.expression = exp;
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
        return new HeapReading(expression);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner(); // This returns the inner type of the reference type
        } else {
            throw new MyException("the rH argument is not a Ref Type");
        }
    }

    @Override
    public String toString() {
        return "HeapReading{" +
                "expression=" + expression.toString() +
                '}';
    }
}
