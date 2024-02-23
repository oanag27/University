package model.adt;

import javafx.util.Pair;
import model.exception.MyException;

import java.util.List;

public interface IBarrierTable extends MyIDictionary<Integer, Pair<Integer, MyIList<Integer>>>{
    int put(Pair<Integer, MyIList<Integer>> value) throws MyException;
    int getFirstFreeLocation();
}
