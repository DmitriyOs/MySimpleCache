package cache.frequency;

import java.util.HashMap;

/**
 * Realisation of Object frequency calculating.
 * @param <K> Key of Object in the Cache
 */
public class FrequencyClass<K> implements FrequencyInterface<K> {
    private HashMap<K, Integer> map = new HashMap<K, Integer>();

    /**
     * Put Key of Object in frequency map with frequency equals 1.
     * @param key Key of Object
     */
    @Override
    public void add(K key) {
        map.put(key, 1);
    }

    /**
     * Put Key of Object in frequency map.
     * @param key Key of Object
     * @param frequency frequency of Key
     */
    @Override
    public void add(K key, int frequency) {
        map.put(key, frequency);
    }

    /**
     * Increment frequency of Key.
     * @param key Key of Object
     */
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

    /**
     * Calculate Key with min frequency.
     * @return Key with min frequency
     */
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