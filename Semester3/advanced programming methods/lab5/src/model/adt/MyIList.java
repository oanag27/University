package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.List;

public interface MyIList<T> {
    T pop(int position) throws EmptyADTException;
    void push(T val);
    List<T> getList();
    boolean isEmpty();
}
