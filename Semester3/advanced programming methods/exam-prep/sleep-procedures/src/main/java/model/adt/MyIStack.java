package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.Stack;

public interface MyIStack<T>{
    T pop() throws EmptyADTException;
    void push(T val);
    Stack<T> getStack();
    boolean isEmpty();
    T peek();
}
