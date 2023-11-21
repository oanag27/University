package test;
import model.value.IntValue;
import model.adt.*;
import model.expression.Exp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class Test {
    public static void main(String[] args) {
        try {
            // Create the expression for the file name
            Exp fileNameExp = new ValueExp(new StringValue("C:/Users/oanam/IdeaProjects/HW2/test.in"));

            // Create the statements
            IStmt openFileStmt = new OpenRFile(fileNameExp);
            IStmt readFirstStmt = new ReadFile(fileNameExp, "varc");
            IStmt printFirstStmt = new PrintStmt(new VarExp("varc"));
            IStmt readSecondStmt = new ReadFile(fileNameExp, "varc");
            IStmt printSecondStmt = new PrintStmt(new VarExp("varc"));
            IStmt closeFileStmt = new CloseRFile(fileNameExp);

            // Create the execution stack
            MyIStack<IStmt> exeStack = new MyStack<>();
            exeStack.push(closeFileStmt);
            exeStack.push(printSecondStmt);
            exeStack.push(readSecondStmt);
            exeStack.push(printFirstStmt);
            exeStack.push(readFirstStmt);
            exeStack.push(openFileStmt);

            // Create the symbol table
            MyIDictionary<String, Value> symTable = new MyDictionary<>();
            symTable.push("varc", new IntValue(0));

            // Create the output list
            MyIList<Value> outputList = new MyList<>();

            // Create the FileTable
            MyIDictionary<Value, BufferedReader> fileTable = new MyDictionary<>();

            // Create the original program statement
            IStmt originalProgram = new NopStmt();

            // Create the ProgramState with the original program
            //ProgramState programState = new ProgramState(exeStack, symTable, outputList, originalProgram,);
            //programState.setFileTable(fileTable);

            // Execute the program
            while (!exeStack.isEmpty()) {
               // exeStack.pop().execute(programState);
               // System.out.println(programState);
            }

            // Display the output list
            System.out.println("Output: " + outputList);

        } catch (Exception e) {
            e.printStackTrace();   //prints stack trace
        }
    }
}
