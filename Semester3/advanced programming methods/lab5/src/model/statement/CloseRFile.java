package model.statement;
import model.adt.MyIDictionary;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.expression.Exp;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;
import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    Exp exp;
    public CloseRFile(Exp exp) {
        this.exp = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();   //retrieve symbol table
        MyIDictionary<Value, BufferedReader> fileTable = state.getFileTable();   //retrieve file table

        // Evaluate expression context symbol table
        Value fileValue = exp.eval(symbolTable);

        // Check if the value is of StringType
        if (!fileValue.getType().equals(new StringType())) {
            throw new MyException("value not of type string");
        }

        StringValue f = (StringValue) fileValue;   //cast to StringValue
        // Check if the file is defined in the FileTable
        if (!fileTable.isDefined(f)) {
            throw new MyException(f+"not defined in file table");
        }

        BufferedReader buff = fileTable.lookUp(f);   //retrieve bufferedReader

        // Close the BufferedReader only if it's not null
        if (buff != null) {
            try {
                buff.close();
                fileTable.pop(f);
                return state;
            } catch (IOException e) {
                throw new MyException(e.getMessage());
            } catch (EmptyADTException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new MyException("BufferedReader is null");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp);
    }
    @Override
    public String toString() {
        return "close the file " + exp;
    }
}
