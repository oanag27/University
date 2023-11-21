package model.expression;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op; // 1->and 2->or
    public LogicExp(Exp ee1,Exp ee2,int opp){
        e1=ee1;
        e2=ee2;
        op=opp;
    }

    @Override
    public String toString() {
        if (op == 1) return e1.toString()+" and "+e2.toString();
        if (op == 2) return e1.toString()+" or "+e2.toString();
        return null;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value v1,v2;
        v1 = e1.eval(tbl);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new BoolType())) {
                BoolValue bool1 = (BoolValue) v1;
                BoolValue bool2 = (BoolValue) v2;
                if(op==1)
                    return new BoolValue(bool1.getVal() && bool2.getVal());
                else if(op==2)
                    return new BoolValue(bool1.getVal() || bool2.getVal());
                else throw new MyException("operation is not ok");
            } else
                throw new MyException("second operand is not boolean");
        } else
            throw new MyException("first operand is not boolean");
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(),op);
    }

}
