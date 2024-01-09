package model.adt;
import model.exception.EmptyADTException;
import model.exception.MyException;
import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    Map<K,V> dictionary;
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
    public Value lookup(K varName) {
        return (Value) dictionary.get(varName);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        return "MyDictionary{" +
                "dictionary=" + dictionary +
                '}';
    }
    @Override
    public MyIDictionary<K,V> deepCopy(){
        MyIDictionary<K,V> dict=new MyDictionary<>();
        for(K key: this.dictionary.keySet()){
            dict.push(key, this.dictionary.get(key));
        }
        return dict;
    }

}