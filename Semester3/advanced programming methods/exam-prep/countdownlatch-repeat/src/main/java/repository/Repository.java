package repository;

import model.exception.MyException;
import model.statement.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<ProgramState> programStateList;
    String fileName;
    public Repository(String file_name) throws MyException {
        try{
            // test if filePath is correct else throw exception
            PrintWriter testPath = new PrintWriter(new BufferedWriter(new FileWriter(file_name, true)));
        } catch (IOException exception) {
            throw new MyException(exception.getMessage());
        }
        programStateList = new ArrayList<>();
        fileName = file_name;
    }
//    @Override
//    public ProgramState getCurrentProgramState() {   //exception
//        return programStateList.get(0);
//    }
//    @Override
//    public List<ProgramState> getAll() {
//        return programStateList;
//    }

    @Override
    public List<ProgramState> getPrgList() {
        return programStateList;
    }

    @Override
    public void setPrgList(List<ProgramState> newPrgState) {
        programStateList = newPrgState;
    }

    @Override
    public void addProgram(ProgramState state) {
        programStateList.add(state);
    }

    @Override
    public void add(ProgramState programState) {
        programStateList.add(programState);
    }

    @Override
    public void logPrgStateExec(ProgramState program) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            logFile.println(program.toString());
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Repository{" +
                "programStateList=" + programStateList +
                '}';
    }
}
