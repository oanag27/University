package model.adt;

import model.value.RefValue;

import java.util.*;

public class MyHeap<V>implements IHeap<Integer,V>{
    private Map<Integer, V> map;
    private Integer freeAddress;
    public MyHeap() {
        this.map = new HashMap<>();
        this.freeAddress = 1;
    }

    @Override
    public String toString() {
        return "MyHeap{" +
                "heapMap=" + map +
                ", freeAddress=" + freeAddress +
                '}'+'\n';
    }

    @Override
    public Integer add(int address, V value) {
        Integer position = this.freeAddress++;
        this.map.put(position,value);
        return position;
    }

    @Override
    public boolean isDefined(Integer address) {
        return map.containsKey(address);
    }

    @Override
    public Integer getFree() {
        return freeAddress++;
    }

    @Override
    public void write(Integer address, V value) {
        map.put(address,value);
    }

    @Override
    public V lookup(Integer addr) {
        return map.get(addr);
    }

    @Override
    public void update(Integer val, V val1) {

    }
    @Override
    public void setContent(Map<Integer, V> integerIValueMap) {
        this.map = new HashMap<>(integerIValueMap);
    }

    @Override
    public Map<Integer, V> getContent() {
        return new HashMap<>(map);
    }

    @Override
    public void safeGarbageCollector(List<Integer> symTableAd) {
        Set<Integer> reachAddr = new HashSet<>(symTableAd);
        reachAddr.addAll(getAddressFromTheHeap());
        map.entrySet().removeIf(entry -> !reachAddr.contains(entry.getKey()));
    }
    private List<Integer> getAddressFromTheHeap(){
        return map.values().stream().filter(v->v instanceof RefValue).map(v->((RefValue)v).getVal()).toList();
    }
}
