package interpreter;

import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyIDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.expression.ArithmExp;
import model.expression.RelationalExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        ProgramState prg1 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                ex1,
                new MyDictionary<Value, BufferedReader>()
        );
        //IRepository repo1 = new Repository(prg1, "log1.txt");
        IRepository repo1 = new Repository("log1.txt");
        Controller ctr1 = new Controller(repo1);
        try {
            ctr1.add(ex1);
        } catch (MyException e) {
            throw new RuntimeException(e);
        } catch (EmptyADTException e) {
            throw new RuntimeException(e);
        }
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithmExp(1, new ValueExp(new IntValue(2)), new ArithmExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithmExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        ProgramState prg2 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                ex2,
                new MyDictionary<Value, BufferedReader>()
        );
        IRepository repo2 = new Repository("log2.txt");
        Controller ctr2 = new Controller(repo2);
        try {
            ctr2.add(ex2);
        } catch (MyException e) {
            throw new RuntimeException(e);
        } catch (EmptyADTException e) {
            throw new RuntimeException(e);
        }
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        ProgramState prg3 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                ex3,
                new MyDictionary<Value, BufferedReader>()
        );
        IRepository repo3 = new Repository("log3.txt");
        Controller ctr3 = new Controller(repo3);
        try {
            ctr3.add(ex3);
        } catch (MyException | EmptyADTException e) {
            System.out.println(e.getMessage());
        }
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg4 = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                ex4,
                new MyDictionary<Value, BufferedReader>()
        );
        IRepository repo4 = new Repository("log4.txt");
        Controller ctr4 = new Controller(repo4);
        try {
            ctr4.add(ex4);
        } catch (MyException | EmptyADTException e) {
            System.out.println(e.getMessage());
        }

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.show();
    }
}




