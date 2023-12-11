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
    public Repository(String file_name) {
        programStateList = new ArrayList<>();
        fileName = file_name;
    }
    @Override
    public ProgramState getCurrentProgramState() {   //exception
        return programStateList.get(0);
    }
    @Override
    public List<ProgramState> getAll() {
        return programStateList;
    }

    @Override
    public void add(ProgramState programState) {
        programStateList.add(programState);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
            for (ProgramState item : getAll()) {
                logFile.println(item.toString());
            }
        } catch (IOException e) {
            throw new MyException(e.getMessage());  //another way
        }
    }

    @Override
    public String toString() {
        return "Repository{" +
                "programStateList=" + programStateList +
                '}';
    }
}
