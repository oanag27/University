package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class NotExp implements Exp{
    Exp exp;
    public NotExp(Exp exp)
    {
        this.exp=exp;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
//        BoolValue value = (BoolValue) exp.eval(tbl, heap);
//        if (value.getVal())
//            return new BoolValue(false);
//        else
//            return new BoolValue(true);
        Value v1;
        v1 = exp.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            BoolValue b1 = (BoolValue) v1;
            boolean firstBool = b1.getVal();
            return new BoolValue(!firstBool);
        } else
            throw new MyException("First operand is not an boolean");
    }

    @Override
    public Exp deepCopy() {
        return new NotExp(exp.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //return exp.typecheck(typeEnv);
        Type type1;
        type1 = exp.typecheck(typeEnv);
        if (type1.equals(new BoolType())) {
            return new BoolType();
        }else
            throw new MyException("First operand is not an boolean");
    }

    @Override
    public String toString() {
        return "NotExp{" +
                "exp=" + exp +
                '}';
    }
}
