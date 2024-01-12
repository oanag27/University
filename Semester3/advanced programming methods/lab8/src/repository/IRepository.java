package repository;

import model.exception.MyException;
import model.statement.ProgramState;

import java.util.List;

public interface IRepository {
    //ProgramState getCurrentProgramState();
    void add(ProgramState programState);
    void logPrgStateExec(ProgramState program) throws MyException;
    ///List<ProgramState> getAll();
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> newPrgState);
    void addProgram(ProgramState state);
}
