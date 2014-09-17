package cache.frequency;

import java.util.HashMap;

public class FrequencyClass<K> implements FrequencyInterface<K> {
    private HashMap<K, Integer> map = new HashMap<K, Integer>();

    @Override
    public void add(K key) {
        map.put(key, 1);
    }

    @Override
    public void add(K key, int frequency) {
        map.put(key, frequency);
    }

    @Override
    public void increment(K key) {
        map.put(key, map.get(key) + 1);
    }

    @Override
    public int get(K key) {
        return map.get(key);
    }

    @Override
    public int remove(K key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public K getMinFrequencyKey() {
        K minKey = null;
        int minValue = Integer.MAX_VALUE;
        for (K key : map.keySet()) {
            if (map.get(key) < minValue) {
                minKey = key;
                minValue = map.get(key);
            }
        }
        return minKey;
    }
}