package model.adt;

import java.util.List;
import java.util.Map;

public interface IHeap<K,V> {
    K add(int address, V value);
    boolean isDefined(K address);
    K getFree();
    void write(K address, V value);
    V lookup(K addr);
    void update(K val, V val1);
    void setContent(Map<K, V> integerIValueMap);
    Map<K,V> getContent();
    void safeGarbageCollector(List<Integer> symTableAddr);
}
