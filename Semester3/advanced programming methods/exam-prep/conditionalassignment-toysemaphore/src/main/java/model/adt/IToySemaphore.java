package model.adt;

import javafx.util.Pair;
import model.exception.MyException;

import java.util.List;
import java.util.Map;

public interface IToySemaphore extends MyIDictionary<Integer, Pair<Integer, Pair<List<Integer>, Integer>>>{
    int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws MyException;
    int getFirstFreeLocation();
}
