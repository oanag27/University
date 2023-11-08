package repository;

import model.statement.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<ProgramState> programStateList;
    public Repository() {
        programStateList = new ArrayList<>();
    }
    @Override
    public ProgramState getCurrentProgramState() {   //exception
        return programStateList.get(0);
    }

    @Override
    public void add(ProgramState programState) {
        programStateList.add(programState);
    }

    @Override
    public String toString() {
        return "Repository{" +
                "programStateList=" + programStateList +
                '}';
    }
}
