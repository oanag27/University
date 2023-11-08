package controller;

import model.adt.MyDictionary;
import model.adt.MyIStack;
import model.adt.MyList;
import model.adt.MyStack;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.statement.IStmt;
import model.statement.ProgramState;
import model.value.Value;
import repository.IRepository;

public class Controller {
    IRepository repo;

    public Controller(IRepository repository) {
        this.repo = repository;
    }
    private ProgramState oneStep(ProgramState state,boolean displayFlag) throws MyException,EmptyADTException {
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk.isEmpty()) throw new EmptyADTException("ProgramState stack is empty");
        IStmt crtStmt = stk.pop();
        crtStmt.execute(state);
        if(displayFlag)
            System.out.println(state.toString());
        return state;
    }

    public void allStep(boolean displayFlag) throws MyException,EmptyADTException{
        ProgramState prg = repo.getCurrentProgramState(); // repo is the controller field of type MyRepoInterface
        //here you can display the prg state
        System.out.println(prg);
        while (!prg.getExeStack().isEmpty()){
            this.oneStep(prg,displayFlag);
        }
        if(!displayFlag)
            System.out.println(prg.toString());
    }
    public void add(IStmt statement) throws MyException, EmptyADTException {
        ProgramState p = new ProgramState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), statement);
        repo.add(p);
    }
}


