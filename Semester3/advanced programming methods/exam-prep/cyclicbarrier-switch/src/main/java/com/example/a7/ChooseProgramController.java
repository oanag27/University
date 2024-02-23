package com.example.a7;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.adt.MyDictionary;
import model.exception.MyException;
import model.expression.*;
import model.statement.*;

import java.io.IOException;

import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

public class ChooseProgramController {
    @FXML
    private ListView<IStmt> listOfPrograms;
    @FXML
    private Label chooseProgramText;

    private RunProgramController runProgramController;

    @FXML
    public void initialize() throws IOException {
        listOfPrograms.setItems(this.getStatements());
        // get second window
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("runProgramStyle.fxml")); //obtain the URL of the FXML file
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load(), 950, 600);
            this.runProgramController = fxmlLoader.getController();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        listOfPrograms.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStmt>() {
            @Override
            public void changed(ObservableValue<? extends IStmt> observableValue, IStmt oldStatement, IStmt newStatement) {
                String filename = "log" + (listOfPrograms.getSelectionModel().getSelectedIndex() + 1) + ".txt";
                runProgramController.setStatement(newStatement, filename);
            }
        });
    }

    private ObservableList<IStmt> getStatements() {
        ObservableList<IStmt> exampleList = FXCollections.observableArrayList();
        IStmt[] examples = new IStmt[]{
                // Example 1
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                                VarExp("v")))),

                // Example 2
                new CompStmt(new VarDeclStmt("a", new IntType()),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new AssignStmt("a", new ArithmExp(1, new ValueExp(new IntValue(2)), new ArithmExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                        new CompStmt(new AssignStmt("b", new ArithmExp(1, new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b")))))),

                // Example 3
                new CompStmt(new VarDeclStmt("a", new BoolType()),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v")))))),

                // Add more examples as needed
                // Example 4
                new CompStmt(new VarDeclStmt("varf", new StringType()),
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
                ),

                // Example 5
                new CompStmt(
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
                ),

                // Example 6
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new PrintStmt(new HeapReading(new VarExp("v"))),
                                        new CompStmt(
                                                new HeapWriting("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ArithmExp(1, new HeapReading(new VarExp("v")), new ValueExp(new IntValue(5))))
                                        )
                                )
                        )
                ),

                // Add more examples as needed
                // Example 7
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new HeapAllocation("a", new VarExp("v")),
                                                new CompStmt(
                                                        new PrintStmt(new HeapReading(new VarExp("v"))),
                                                        new PrintStmt(new ArithmExp(1, new HeapReading(new HeapReading(new VarExp("a"))), new ValueExp(new IntValue(5))))
                                                )
                                        )
                                )
                        )
                ),

                // Example 8
                new CompStmt(
                        new VarDeclStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new HeapAllocation("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new HeapAllocation("a", new VarExp("v")),
                                                new CompStmt(
                                                        new HeapAllocation("v", new ValueExp(new IntValue(30))),
                                                        new PrintStmt(new HeapReading(new HeapReading(new VarExp("a")))))
                                        )
                                )
                        )
                ),

                //Example 9
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(4))),
                                new CompStmt(
                                        new WhileStatement(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v", new ArithmExp(2, new VarExp("v"), new ValueExp(new IntValue(1))))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                ),

                // Example 10
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new IntType())),
                                new CompStmt(
                                        new AssignStmt("v", new ValueExp(new IntValue(10))),
                                        new CompStmt(
                                                new HeapAllocation("a", new ValueExp(new IntValue(22))),
                                                new CompStmt(
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
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new HeapReading(new VarExp("a")))
                                                        )
                                                )
                                        )
                                )
                        )
                ),

                // Example 11
                new CompStmt(
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
                ),
                // Example 12
                // a=1;b=2;c=5;
                //switch(a*10)
                // (case (b*c) print(a);print(b))
                // (case (10) print(100);print(200))
                // (default print(300));
                //print(300)
                new CompStmt(new VarDeclStmt("a", new IntType()),
                        new CompStmt(new VarDeclStmt("b", new IntType()),
                                new CompStmt(new VarDeclStmt("c", new IntType()),
                                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(1))),
                                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                        new CompStmt(new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                                new CompStmt(new Switch(new ArithmExp(3, new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                        new ArithmExp(3, new VarExp("b"), new VarExp("c")),
                                                                        new ValueExp(new IntValue(10)),
                                                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))))))))),
                // Example 13
                //new(v1,2);new(v2,3);new(v3,4);newBarrier(cnt,rH(v2));
                //fork(await(cnt);wh(v1,rh(v1)*10));print(rh(v1)));
                //fork(await(cnt);wh(v2,rh(v2)*10));wh(v2,rh(v2)*10));print(rh(v2)));
                //await(cnt);
                //print(rH(v3))
                //Final out:{4,20,300}

                new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                                new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                        new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                                new CompStmt(new HeapAllocation("v1", new ValueExp(new IntValue(2))),
                                                        new CompStmt(new HeapAllocation("v2", new ValueExp(new IntValue(3))),
                                                                new CompStmt(new HeapAllocation("v3", new ValueExp(new IntValue(4))),
                                                                        new CompStmt(new BarrierStmt("cnt", new HeapReading(new VarExp("v2"))),
                                                                                new CompStmt(new ForkStatement(new CompStmt(new AwaitStmt("cnt"),
                                                                                        new CompStmt(new HeapWriting("v1", new ArithmExp(3, new HeapReading(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                new PrintStmt(new HeapReading(new VarExp("v1")))))),
                                                                                        new CompStmt(new ForkStatement(new CompStmt(new AwaitStmt("cnt"),
                                                                                                new CompStmt(new HeapWriting("v2", new ArithmExp(3, new HeapReading(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                        new CompStmt(new HeapWriting("v2", new ArithmExp(3, new HeapReading(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                new PrintStmt(new HeapReading(new VarExp("v2"))))))),
                                                                                                new CompStmt(new AwaitStmt("cnt"),
                                                                                                        new PrintStmt(new HeapReading(new VarExp("v3")))))))))))))),
        };

        for (IStmt example : examples) {
            try {
                example.typecheck(new MyDictionary<>());
                exampleList.add(example);
            } catch (MyException exception) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Program that did not pass:\n" + example.toString() + "\n due to:\n" + exception.getMessage());
                error.showAndWait();
            }
        }
        return exampleList;
    }
}
