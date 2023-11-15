package model.adt;
import model.exception.EmptyADTException;
import model.exception.MyException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    HashMap<K,V> dictionary;
    public MyDictionary() {
        dictionary = new HashMap<K,V>();
    }
    @Override
    public boolean isDefined(K key) {
        return dictionary.get(key)!=null;
    }

    @Override
    public void push(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        return dictionary.get(key);
    }

    @Override
    public V pop(K key) throws EmptyADTException {
        if(isEmpty())
            throw new EmptyADTException("Dictionary is empty");
        return dictionary.remove(key);
    }
    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public Set<K> getKeys() {
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        return "MyDictionary{" +
                "dictionary=" + dictionary +
                '}';
    }

}
