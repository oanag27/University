package controller;

import model.adt.*;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.statement.IStmt;
import model.statement.ProgramState;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;

    public Controller(IRepository repository) {
        this.repo = repository;
    }

    private ProgramState oneStep(ProgramState state, boolean displayFlag) throws MyException, EmptyADTException {
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk.isEmpty()) throw new EmptyADTException("ProgramState stack is empty");
        IStmt crtStmt = stk.pop();
        crtStmt.execute(state);
        state.getHeap().safeGarbageCollector(getAddrFromSymTable(state.getSymTable().getContent().values()));
        if (displayFlag)
            System.out.println(state);
        return state;
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getVal();
                })
                .collect(Collectors.toList());
    }

    public void allStep(boolean displayFlag) throws MyException, EmptyADTException {
        ProgramState prg = repo.getCurrentProgramState(); // repo is the controller field of type RepoInterface
        //here you can display the prg state
        System.out.println(prg);
        repo.logPrgStateExec();
        while (!prg.getExeStack().isEmpty()) {
            this.oneStep(prg, displayFlag);
            repo.logPrgStateExec();
        }
        if (!displayFlag)
            System.out.println(prg);
    }

    public void add(IStmt statement) throws MyException, EmptyADTException {
        MyStack<IStmt> stack = new MyStack<>();
        MyDictionary<String, Value> symMap = new MyDictionary<>();
        MyList<Value> out = new MyList<>();
        MyIDictionary<Value, BufferedReader> fileTable = new MyDictionary<>();
        IHeap<Integer, Value> heap = new MyHeap<>();

        ProgramState p = new ProgramState(stack, symMap, out, statement, fileTable, heap);
        repo.add(p);
    }

}


