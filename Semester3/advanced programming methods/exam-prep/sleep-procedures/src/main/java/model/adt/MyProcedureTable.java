package model.adt;

import javafx.util.Pair;
import model.statement.IStmt;

import java.util.List;

public class MyProcedureTable extends MyDictionary<String, Pair<List<String>, IStmt>> implements IProcedureTable{
}
