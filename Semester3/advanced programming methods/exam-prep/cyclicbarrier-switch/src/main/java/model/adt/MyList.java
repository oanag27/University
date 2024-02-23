package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    List<T> items;

    public MyList() {
        items = new ArrayList<>();
    }

    @Override
    public T pop(int position) throws EmptyADTException {
//        if(isEmpty())
//            throw new EmptyADTException("List is empty");
        if (isEmpty() || position < 0 || position >= items.size()) {
            throw new EmptyADTException("List is empty or the position is invalid");
        }
        return items.remove(position);
    }

    //    @Override
//    public String toString() {
//        String result="";
//        for (T var:items) {
//            result += var.toString();
//            result +=" ";
//        }
//        return result;
//    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (T var : items) {
            if (var != null) {
                result.append(var.toString()).append(" ");
            } else {
                result.append("null").append(" ");
            }
        }
        return result.toString();
    }

    @Override
    public void push(T itemToAdd) {
        items.add(itemToAdd);
    }

    @Override
    public List<T> getList() {
        return items;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

}
