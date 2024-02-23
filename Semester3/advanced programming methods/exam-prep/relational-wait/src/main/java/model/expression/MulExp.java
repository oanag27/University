package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.Value;

public class MulExp implements Exp{
    private final Exp e1;
    private final Exp e2;
    public MulExp(Exp e1, Exp e2){
        this.e1=e1;
        this.e2=e2;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Exp converted = new ArithmExp(2,
                new ArithmExp(3, e1, e2),
                new ArithmExp(1, e1, e2));
        return converted.eval(tbl, heap);
    }

    @Override
    public Exp deepCopy() {
        return new MulExp(e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = e1.typecheck(typeEnv);
        Type type2 = e2.typecheck(typeEnv);
        if (type1.equals(new IntType()) && type2.equals(new IntType()))
            return new IntType();
        else
            throw new MyException("Expressions in the MUL should be int!");
    }

    @Override
    public String toString() {
        return "MulExp{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                '}';
    }
}
