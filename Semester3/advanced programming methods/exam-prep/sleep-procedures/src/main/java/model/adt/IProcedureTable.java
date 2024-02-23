package model.adt;

import javafx.util.Pair;
import model.statement.IStmt;

import java.util.List;

public interface IProcedureTable extends MyIDictionary<String, Pair<List<String>, IStmt>>{
}
