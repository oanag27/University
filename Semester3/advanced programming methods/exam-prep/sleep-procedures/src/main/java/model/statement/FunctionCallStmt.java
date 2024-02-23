package model.statement;

import javafx.util.Pair;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyList;
import model.exception.MyException;
import model.expression.Exp;
import model.type.Type;
import model.value.Value;

import java.util.List;
import java.util.Vector;

public class FunctionCallStmt implements IStmt{
    private String functionName;
    private MyList<Exp> parameters;

    public FunctionCallStmt(String functionName, List<Exp> parameters) throws MyException {
        this.functionName = functionName;
        this.parameters = new MyList<Exp>();
        for (int i = 0; i < parameters.size(); ++i) {
            this.parameters.push(parameters.get(i));
        }
        System.out.println("FunctionCallStmt created: " + functionName); // Add this line
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException, MyException, MyException {
        try {
            Pair<List<String>, IStmt> functionEntry = state.getProcedureTable().lookUp(functionName);
            if (functionEntry == null)
                throw new MyException(String.format("Function '%s' does not exist!", functionName));

            List<String> paramNames = functionEntry.getKey();
            IStmt functionBody = functionEntry.getValue();

            List<Value> paramValues = new Vector<Value>();
            for (int i = 0; i < parameters.size(); ++i)
                paramValues.add(parameters.get(i).eval(state.getSymTable(), state.getHeap()));

            MyIDictionary<String, Value> newSymbolsTable = new MyDictionary<>();
            int size = paramNames.size();
            for (int i = 0; i < size; ++i)
                newSymbolsTable.put(paramNames.get(i), paramValues.get(i));

            state.getAllSymTables().push(newSymbolsTable);
            state.getExeStack().push(new FunctionReturnStmt());
            state.getExeStack().push(functionBody);
        } catch (MyException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        List<Exp> newParams = new Vector<Exp>();
        for (int i = 0; i < parameters.size(); ++i) {
            try {
                newParams.add(parameters.get(i).deepCopy());
            } catch (MyException e) {
                return null;
            }
        }

        try {
            return new FunctionCallStmt(functionName, newParams);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
//        return "FunctionCallStmt{" +
//                "functionName='" + functionName + '\'' +
//                ", parameters=" + parameters +
//                '}';
        StringBuilder result = new StringBuilder("call " + functionName + "(");
        for (int i = 0; i < parameters.size() - 1; ++i) {
            try {
                result.append(parameters.get(i).toString()).append(", ");
            } catch (MyException e) {
                return null;
            }
        }

        if (parameters.size() > 0) {
            try {
                result.append(parameters.get(parameters.size() - 1).toString());
                result.append(")");
            } catch (MyException e) {
                return null;
            }
        }

        return result.toString();
    }
}
