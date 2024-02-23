package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

public class ArithmExp implements Exp{
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide
    public ArithmExp(int oper, Exp ee1, Exp ee2){
        e1 = ee1;
        e2 = ee2;
        op = oper;
    }

    @Override
    public String toString(){
        if (op == 1) return e1.toString()+"+"+e2.toString();
        if (op == 2) return e1.toString()+"-"+e2.toString();
        if (op == 3) return e1.toString()+"*"+e2.toString();
        if (op == 4) return e1.toString()+"/"+e2.toString();
        return null;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                Integer n1;
                Integer n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4)
                    if (n2 == 0) throw new MyException("cannot divide by 0");
                    else return new IntValue(n1 / n2);
                else throw new MyException("operation performed is not ok");
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new ArithmExp(op,e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

//    @Override
//    public Value eval(MyIDictionary<String, Value> symTable, IHeap<Integer, Value> heap) throws MyException{
//        Value v1, v2;
//        v1 = e1.eval(symTable,heap);
//        if (v1.getType().equals(new IntType())) {
//            v2 = e2.eval(symTable,heap);
//            if (v2.getType().equals(new IntType())) {
//                IntValue i1 = (IntValue) v1;
//                IntValue i2 = (IntValue) v2;
//                int n1, n2;
//                n1 = i1.getVal();
//                n2 = i2.getVal();
//                if (op == 1) return new IntValue(n1 + n2);
//                if (op == 2) return new IntValue(n1 - n2);
//                if (op == 3) return new IntValue(n1 * n2);
//                if (op == 4) {
//                    if (n2 == 0) {
//                        throw new MyException("Cannot divide by zero");
//                    } else return new IntValue(n1 / n2);
//                } else throw new MyException("Operation is not good");
//            } else throw new MyException("Second operator is not an integer");
//        } else throw new MyException("First operand is not an integer");
//    }

}
