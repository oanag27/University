package model.expression;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.Type;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private String operator; // "<", "<=", "==", "!=", ">", ">="
    public RelationalExp(Exp exp1, Exp exp2, String operator) {
            this.exp1 = exp1;
            this.exp2 = exp2;
            this.operator = operator;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value value1 = exp1.eval(tbl, heap);
        Value value2 = exp2.eval(tbl, heap);
        if(value1.getType() instanceof IntValue || value2.getType() instanceof IntValue)
        {
            throw new MyException("one the operands is not an integer");
        }

        switch (operator) {
            case "<":
                return new BoolValue(((IntValue) value1).getVal() < ((IntValue) value2).getVal());
            case "<=":
                return new BoolValue(((IntValue) value1).getVal() <= ((IntValue) value2).getVal());
            case "==":
                return new BoolValue(value1.equals(value2));
            case "!=":
                return new BoolValue(!value1.equals(value2));
            case ">":
                return new BoolValue(((IntValue) value1).getVal() > ((IntValue) value2).getVal());
            case ">=":
                return new BoolValue(((IntValue) value1).getVal() >= ((IntValue) value2).getVal());
            default:
                throw new MyException("Invalid relational operator: " + operator);
        }
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(exp1.deepCopy(),exp2.deepCopy(),operator);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
