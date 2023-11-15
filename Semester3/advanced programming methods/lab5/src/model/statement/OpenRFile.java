package model.statement;
import model.adt.MyIDictionary;
import model.exception.MyException;
import model.expression.Exp;
import model.value.StringValue;
import model.value.Value;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    Exp exp;
    public OpenRFile(Exp e){
        exp = e;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();  //retrieve symbol table
        MyIDictionary<Value, BufferedReader> fileTable = state.getFileTable(); //retrieve file table
        // Evaluate the expression
        Value fileValue = exp.eval(symbolTable);
        // Check if the value is of StringType
        if (!(fileValue instanceof StringValue)) {
            throw new MyException("File name is not a string type");
        }
        StringValue f = (StringValue) fileValue;   //cast to a StringValue
        // Check if the file is not already in the FileTable
        if (fileTable.isDefined(f)) {
            throw new MyException("File already opened");
        }
        BufferedReader buff = null;
        try {
            //opening a file for reading
            FileReader fileread = new FileReader((String) f.getVal());   //creates a FileReader
            buff = new BufferedReader(fileread);

            // Add the file to the FileTable
            fileTable.update(f, buff);
            return state;
        } catch (FileNotFoundException e) {   //catch exception for fileReader
            throw new MyException(e.getMessage());
        }
    }
    @Override
    public IStmt deepCopy() {
        return new OpenRFile(exp);
    }
    @Override
    public String toString() {
        return "open " + exp.toString();
    }
}
