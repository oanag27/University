package view;
import controller.Controller;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.expression.ArithmExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import java.util.Scanner;
public class View {
    private final Controller controller;
    private final Scanner scanner;
    public View(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }
    private void printMenu() {
        System.out.println("Choose an example to run:");
        System.out.println("1 - Example 1");
        System.out.println("2 - Example 2");
        System.out.println("3 - Example 3");
        System.out.println("0 - Exit");
        System.out.println("Option chosen is: ");
    }
    public void execute() {
        while (true) {
            printMenu();
            int userCommand = scanner.nextInt();
            switch (userCommand) {
                case 1:
                    runExample1();
                    break;
                case 2:
                    runExample2();
                    break;
                case 3:
                    runExample3();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    private void runExample1() {
        IStmt example1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        executeExample(example1);
    }
    private void runExample2() {
        IStmt example2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithmExp(1, new ValueExp(new IntValue(2)), new ArithmExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithmExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        executeExample(example2);
    }
    private void runExample3() {
        IStmt example3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        executeExample(example3);
    }
    private void executeExample(IStmt statement) {
        try {
            controller.add(statement);
            controller.allStep(true);
        } catch (MyException e) {
            System.out.println("ERROR: " + e);
        } catch (EmptyADTException e) {
            System.out.println("ERROR: " + e);
        }
    }
}


