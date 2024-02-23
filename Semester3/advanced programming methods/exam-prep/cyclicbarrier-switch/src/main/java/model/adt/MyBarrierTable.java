package model.adt;

import javafx.util.Pair;
import model.exception.MyException;

import java.util.List;

public class MyBarrierTable extends MyDictionary<Integer, Pair<Integer, MyIList<Integer>>> implements IBarrierTable{
    private int nextFreeLocation;

    public MyBarrierTable() {
        super();
        this.nextFreeLocation = 1;
    }
    public void put(Integer key, Pair<Integer, MyIList<Integer>> value) throws MyException {
        if (!key.equals(nextFreeLocation))
            throw new MyException("Invalid lock table location!");
        super.put(key, value);
        synchronized (this) {
            nextFreeLocation++;
        }
    }

    @Override
    public int put(Pair<Integer, MyIList<Integer>> value) throws MyException {
        super.put(nextFreeLocation, value);
        synchronized (this) {
            nextFreeLocation++;
        }
        return nextFreeLocation - 1;
    }
    @Override
    public int getFirstFreeLocation() {
        return 0;
    }
}
