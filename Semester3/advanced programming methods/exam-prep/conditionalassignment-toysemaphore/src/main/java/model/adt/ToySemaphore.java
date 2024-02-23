package model.adt;

import javafx.util.Pair;
import model.exception.MyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ToySemaphore extends MyDictionary<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> implements IToySemaphore{

    private int nextFreeLocation;

    public ToySemaphore() {
        super();
        this.nextFreeLocation = 1;
    }

    public void put(Integer key, Pair<Integer, Pair<List<Integer>, Integer>> value){
        if (!key.equals(nextFreeLocation))
            try {
                throw new MyException("Invalid semaphore table location!");
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        super.put(key, value);
        synchronized (this) {
            nextFreeLocation++;
        }
    }

    @Override
    public int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws MyException {
        super.put(nextFreeLocation, value);
        synchronized (this) {
            nextFreeLocation++;
        }
        return nextFreeLocation - 1;
    }
    public int getFirstFreeLocation() {
        int locationAddress = 1;
        while (this.dictionary.get(locationAddress) != null)
            locationAddress++;
        return locationAddress;
    }
}
