package repository;

import model.exception.MyException;
import model.statement.ProgramState;

import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void add(ProgramState programState);
    void logPrgStateExec() throws MyException;
    List<ProgramState> getAll();
}
