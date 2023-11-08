package repository;

import model.statement.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void add(ProgramState programState);
}
