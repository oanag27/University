package model.statement;

import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.type.IntType;
import model.type.StringType;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    Exp exp;
    String varName;
    public ReadFile(Exp expr,String var_name) {
        varName = var_name;
        exp = expr;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();    //retrieve symbol table
        MyIDictionary<Value, BufferedReader> fileTable = state.getFileTable();  //retrieve file table
        // Check if varName is defined in SymTable and its type is int
        if (!symbolTable.isDefined(varName)) {
            throw new MyException("variable not in the table"+varName);
        }
        if (!symbolTable.lookUp(varName).getType().equals(new IntType())) {
            throw new MyException("variable is not type int"+varName);
        }
        // Evaluate expression in context of symbol table
        Value fileVal = exp.eval(symbolTable);
        // Check if the value is of StringType
        // Check if getType() is not null before calling equals
        if (fileVal.getType() == null ||fileVal.getType() == null ||!fileVal.getType().equals(new StringType())) {
            throw new MyException("file is not type string");
        }
        // Cast fileVal to StringValue
        StringValue f = (StringValue) fileVal;
        // Check if file is defined in the FileTable
        if (!fileTable.isDefined(f)) {
            throw new MyException(f+"not defined in the table");
        }
        BufferedReader buff = fileTable.lookUp(f);  //retrieve bufferedReader associated to fileTable
        IntValue intValue;
        try {
           // buf = fileTbl.lookUp(file);
            String read = buff.readLine();
            // Close the BufferedReader after reading
            //buf.close();
            if (read == null) {
                intValue = new IntValue(0);
            } else {
                intValue = new IntValue(Integer.parseInt(read));   //converts to int
            }
            // Update SymTable with the computed int value
            symbolTable.update(varName, intValue);
            return state;
        } catch (IOException | NumberFormatException e) {
            throw new MyException(e.getMessage());
        }
    }
    @Override
    public String toString() {
        return "Read from " + exp +
                " variable " + varName;
    }
    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp,varName);
    }
}
