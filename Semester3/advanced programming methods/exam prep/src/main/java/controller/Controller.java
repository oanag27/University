
package controller;
import model.adt.*;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.statement.IStmt;
import model.statement.ProgramState;
import model.value.RefValue;
import model.value.StringValue;
import model.value.Value;
import repository.Repository;
import repository.IRepository;
import java.io.BufferedReader;
import java.security.Key;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    IRepository repo;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repo = repository;
        displayFlag = false;
    }
    public List<ProgramState> getAllPrograms(){
        return this.repo.getPrgList();
    }

    // returns a list of integer addresses extracted from RefValue instances
    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getVal();
                })
                .collect(Collectors.toList());
    }
    public void oneStepForAllPrgGUI() throws MyException{
        this.executor= Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<ProgramState> prgList=removeCompletedPrg(repo.getPrgList());
        repo.setPrgList(prgList);
        if(prgList.size() == 0)
            throw new MyException("Program is done!");
        conservativeGarbageCollector(prgList);
        oneStepForAllPrg(prgList);
        executor.shutdown();
        // HERE the repository still contains at lest one Completed Prg
        //and its List<PrgStat> is not empty, note that oneStepForAllPrg calls for method
        // setPrgList of repository in order to change the repository
        // update the repo state

    }


    public String displayState(ProgramState state) {
        return state.toString();
    }

    public void setDisplayAll(Boolean flag) {
        this.displayFlag = flag;
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValue, Collection<Value> heap) {
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof RefValue)  //check whether they are instances of the RefValue class
                        .map(v -> {
                            RefValue v1 = (RefValue) v;
                            return v1.getVal();
                        }),
                symbolTableValue.stream()
                        .filter(v -> v instanceof RefValue) //check whether they are instances of the RefValue class
                        .map(v -> {
                            RefValue v1 = (RefValue) v;
                            return v1.getVal();
                        })
        ).collect(Collectors.toList());  //collects all the elements of the stream into a list
    }

    void oneStepForAllPrg(List<ProgramState> prgList) throws MyException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        try {
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            //add the new created threads to the list of existing threads
            prgList.addAll(newProgramList);
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }
        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });
        //Save the current programs in the repository
        repo.setPrgList(prgList);

    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            //HERE you can call conservativeGarbageCollector
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        //HERE you can call conservativeGarbageCollector
        executor.shutdownNow();
        //HERE you can call conservativeGarbageCollector
        //repo.setPrgList(prgList);
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        repo.setPrgList(prgList);
        // update the repository state

    }

    // performs a garbage collection operation on the heap of each ProgramState in the prgList
    // based on the addresses obtained from their symbol tables
    private void conservativeGarbageCollector(List<ProgramState> prgList) {
        var heap = Objects.requireNonNull(prgList.stream()    //ensure that the resulting stream is not null
                .map(p -> getAddressesFromSymbolTable(       //extracts the addresses from the symbol table and heap
                        p.getSymTable().getContent().values(),
                        p.getHeap().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());  //concatenates these streams into a single stream
        prgList.forEach(programState -> {
            programState.getHeap().setContent(
                    safeGarbageCollector(
                            heap,
                            prgList.get(0).getHeap().getContent()
                    ));
        });
    }

    // filters the entries of the heap map based on whether their keys are present in the symbolTableAddresses list
    // and returns the resulting map
    private Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> prgList) {
        //return prgList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
        return prgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
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
//    public void typeCheck() throws MyException {
//        this.repo.getPrgList().get(0).typeCheck();
//    }
    public IHeap<Integer,Value> getHeapTableGUI() {return this.getAllPrograms().get(0).getHeap();}
    public MyIList<Value> getOutGUI() {return this.getAllPrograms().get(0).getOut();}
    public MyIDictionary<Value, BufferedReader> getFileTableGUI() {
        return this.getAllPrograms().get(0).getFileTable();
    }

}


