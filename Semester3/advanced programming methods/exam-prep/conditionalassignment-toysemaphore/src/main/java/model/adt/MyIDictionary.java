package model.adt;

import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,V> {
    boolean isDefined(K key);
    void push(K key, V value);
    V lookUp(K key);
    V pop(K key) throws EmptyADTException;
    void update(K key, V value);
    boolean isEmpty();
    Set<K> getKeys();
    V lookup(K varName);
    Map<K, V> getContent();

    MyIDictionary<K, V> deepCopy() throws MyException;

    void put(K variableName, V intValue);
}
