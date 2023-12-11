package interpreter;

import controller.Controller;
import model.adt.MyDictionary;
import model.adt.MyHeap;
import model.adt.MyList;
import model.adt.MyStack;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.expression.*;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
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
    public static void main(String[] args) throws MyException, EmptyADTException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
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
        IRepository repo4 = new Repository("log4.txt");
        Controller ctr4 = new Controller(repo4);
        try {
            ctr4.add(ex4);
        } catch (MyException | EmptyADTException e) {
            System.out.println(e.getMessage());
        }
        // Example 5
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );
        IRepository repo5 = new Repository("log5.txt");
        Controller ctr5 = new Controller(repo5);
        try {
            ctr5.add(ex5);
        }catch (MyException e)
        {
            System.out.println(e.getMessage());
        }
        // Example 6
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex6 =
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new PrintStmt(new HeapReading(new VarExp("v"), new MyHeap<>())),
                                        new CompStmt(
                                                new HeapWriting("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ArithmExp(1, new HeapReading(new VarExp("v"), new MyHeap<>()),
                                                        new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                );
        IRepository repo6 = new Repository("log6.txt");
        Controller ctr6 = new Controller(repo6);
        try{
            ctr6.add(ex6);
        }catch(MyException e)
        {
            System.out.println(e.getMessage());
        }

        // Example 7
        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new HeapReading(new VarExp("v"), new MyHeap<>())),
                                                new PrintStmt(new ArithmExp(1, new HeapReading(new HeapReading
                                                        (new VarExp("a"), new MyHeap<>()), new MyHeap<>()), new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                )
        );
        IRepository repo7 = new Repository("log7.txt");
        Controller ctr7 = new Controller(repo7);
        try{
            ctr7.add(ex7);
        }catch (MyException e)
        {
            System.out.println(e.getMessage());
        }
        // Example 8
        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(
                                                new HeapAllocation("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReading(new HeapReading(new VarExp("a"),
                                                        new MyHeap<>()), new MyHeap<>()))
                                        )
                                )
                        )
                )
        );
        IRepository repo8 = new Repository("log8.txt");
        Controller ctr8 = new Controller(repo8);
        try {
            ctr8.add(ex8);
        }catch (MyException e)
        {
            System.out.println(e.getMessage());
        }
        //Example 9
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex9 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new WhileStatement(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithmExp(2, new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        )
                )
        );
        IRepository repo9 = new Repository("log9.txt");
        Controller ctr9 = new Controller(repo9);
        try {
            ctr9.add(ex9);
        }catch(MyException e)
        {
            System.out.println(e.getMessage());
        }
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.show();
    }
}




