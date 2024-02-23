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
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        try {
            ex1.typecheck(new MyDictionary<>());
            IRepository repo1 = new Repository("log1.txt");
            Controller ctr1 = new Controller(repo1);
            ctr1.add(ex1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        } catch (MyException e) {
            throw new RuntimeException(e);
        } catch (EmptyADTException e) {
            throw new RuntimeException(e);
        }
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithmExp(1, new ValueExp(new IntValue(2)), new ArithmExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithmExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        try {
            ex2.typecheck(new MyDictionary<>());
            IRepository repo2 = new Repository("log2.txt");
            Controller ctr2 = new Controller(repo2);
            ctr2.add(ex2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
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

        try {
            ex3.typecheck(new MyDictionary<>());
            IRepository repo3 = new Repository("log3.txt");
            Controller ctr3 = new Controller(repo3);
            ctr3.add(ex3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
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

        try {
            ex4.typecheck(new MyDictionary<>());
            IRepository repo4 = new Repository("log4.txt");
            Controller ctr4 = new Controller(repo4);
            ctr4.add(ex4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
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

        try {
            ex5.typecheck(new MyDictionary<>());
            IRepository repo5 = new Repository("log5.txt");
            Controller ctr5 = new Controller(repo5);
            ctr5.add(ex5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        } catch (MyException e) {
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
                                        new PrintStmt(new HeapReading(new VarExp("v"))),
                                        new CompStmt(
                                                new HeapWriting("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ArithmExp(1, new HeapReading(new VarExp("v")),
                                                        new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                );

        try {
            ex6.typecheck(new MyDictionary<>());
            IRepository repo6 = new Repository("log6.txt");
            Controller ctr6 = new Controller(repo6);
            ctr6.add(ex6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        } catch (MyException e) {
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
                                                new PrintStmt(new HeapReading(new VarExp("v"))),
                                                new PrintStmt(new ArithmExp(1, new HeapReading(new HeapReading
                                                        (new VarExp("a"))), new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                )
        );

        try {
            ex7.typecheck(new MyDictionary<>());
            IRepository repo7 = new Repository("log7.txt");
            Controller ctr7 = new Controller(repo7);
            ctr7.add(ex7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        } catch (MyException e) {
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
                                                new PrintStmt(new HeapReading(new HeapReading(new VarExp("a")
                                                )))
                                        )
                                )
                        )
                )
        );

        try {
            ex8.typecheck(new MyDictionary<>());
            IRepository repo8 = new Repository("log8.txt");
            Controller ctr8 = new Controller(repo8);
            ctr8.add(ex8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        } catch (MyException e) {
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

        try {
            ex9.typecheck(new MyDictionary<>());
            IRepository repo9 = new Repository("log9.txt");
            Controller ctr9 = new Controller(repo9);
            ctr9.add(ex9);
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        // Example 10
        //int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStmt ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new HeapAllocation("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                // Forked thread
                                                new ForkStatement(
                                                        new CompStmt(
                                                                new HeapWriting("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new HeapReading(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                // Original thread
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReading(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );


        try {
            ex10.typecheck(new MyDictionary<>());
            IRepository repo10 = new Repository("log12.txt");
            Controller ctr10 = new Controller(repo10);
            ctr10.add(ex10);
            menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        //Example 11
        //Ref int a;
        //int counter;
        //while(counter<10){fork(fork(new(a,counter));print(rH(a)));counter=counter+1;};
        IStmt ex11 = new CompStmt(
                new VarDeclStmt("counter", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new WhileStatement(
                                        new RelationalExp(
                                                new VarExp("counter"),
                                                new ValueExp(new IntValue(10)),
                                                "<"
                                        ),
                                        new CompStmt(
                                                new ForkStatement(
                                                        new ForkStatement(
                                                                new CompStmt(
                                                                        new HeapAllocation("a", new VarExp("counter")),
                                                                        new PrintStmt(new HeapReading(new VarExp("a")))
                                                                )
                                                        )
                                                ),
                                                new AssignStmt("counter", new ArithmExp(
                                                        1,
                                                        new VarExp("counter"),
                                                        new ValueExp(new IntValue(1))

                                                ))
                                        )
                                ),
                                new PrintStmt(new VarExp("counter"))
                        )
                )
        );
        try{
            ex11.typecheck(new MyDictionary<>());
            IRepository repo11 = new Repository("log13.txt");
            Controller ctr11 = new Controller(repo11);
            ctr11.add(ex11);
            menu.addCommand(new RunExample("11", ex11.toString(),ctr11));
        }catch(MyException e)
        {
            System.out.println(e.getMessage());
        }

        menu.show();
    }
}




