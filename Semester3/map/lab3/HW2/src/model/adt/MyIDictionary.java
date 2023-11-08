package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

public interface MyIDictionary<K,V> {
    boolean isDefined(K key);
    void push(K key, V value);
    V lookUp(K key);
    V pop(K key) throws EmptyADTException;
    void update(K key, V value);
    boolean isEmpty();
}
