package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;    //CollectionType
    public MyStack() { stack = new Stack<T>();}
    @Override
    public T pop() throws EmptyADTException {
        if(isEmpty())
            throw new EmptyADTException("Stack is empty");
        return stack.pop();
    }
    @Override
    public void push(T val) {
        stack.push(val);
    }
    @Override
    public String toString() {
        return "MyStack{" +
                "stack=" + stack +
                '}';
    }
    @Override
    public Stack<T> getStack() {
        return stack;
    }
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
